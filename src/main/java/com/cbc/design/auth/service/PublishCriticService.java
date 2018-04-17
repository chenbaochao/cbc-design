package com.cbc.design.auth.service;

import com.cbc.design.auth.domain.PublishCritic;
import com.cbc.design.auth.domain.User;
import com.cbc.design.auth.repositories.*;
import com.cbc.design.auth.web.dto.MyFriendDTO;
import com.cbc.design.auth.web.dto.UserPublishDTO;
import com.cbc.design.common.*;
import com.cbc.design.common.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by cbc on 2018/4/1.
 */
@Service
public class PublishCriticService {


    private final PublishCriticRepository publishCriticRepository;

    private final FriendRepository friendRepository;

    private final CollectionCriticRepository collectionCriticRepository;

    private final CommentCriticRepository commentCriticRepository;

    private final GoodCriticRepository goodCriticRepository;

    @Value("${file.path}")
    private String filePath;

    public PublishCriticService(PublishCriticRepository publishCriticRepository, FriendRepository friendRepository, CollectionCriticRepository collectionCriticRepository, CommentCriticRepository commentCriticRepository, GoodCriticRepository goodCriticRepository) {
        this.publishCriticRepository = publishCriticRepository;
        this.friendRepository = friendRepository;
        this.collectionCriticRepository = collectionCriticRepository;
        this.commentCriticRepository = commentCriticRepository;
        this.goodCriticRepository = goodCriticRepository;
    }

    /**
     * 获取用户说说
     *
     * @param userIds
     * @return
     */
    public List<UserPublishDTO> getUserPublish(List<Long> userIds, Pageable pageable) {
        List<UserPublishDTO> publishCritics = publishCriticRepository.findByUserIdInOrderByUpdateDateDesc(
                userIds, pageable
        ).getContent();
        return fillUserPublishDTO(publishCritics);
    }


    private List<UserPublishDTO> fillUserPublishDTO(List<UserPublishDTO> publishCritics){
           return  publishCritics.stream().map(
                    u -> {
                        Long publishCriticId = u.getPublishCriticId();
                        u
                                .setCollectionCounts(collectionCriticRepository.countByPublishCriticIdAndIsAllowTrue(publishCriticId))
                                .setCommentCounts(commentCriticRepository.countByPublishCriticIdAndIsAllowTrue(publishCriticId))
                                .setGoodCounts(goodCriticRepository.countByPublishCriticIdAndIsAllowTrue(publishCriticId))
                                .setIsGood(goodCriticRepository.existsByUserIdAndPublishCriticIdAndIsAllowTrue(u.getUserId(), publishCriticId))
                                .setIsCollection(collectionCriticRepository.existsByUserIdAndPublishCriticIdAndIsAllowTrue(u.getUserId(), publishCriticId));
                        return u;
                    }
            ).collect(Collectors.toList());
        }

    /**
     * 获取用户说说
     *
     * @param user
     * @return
     */
    public List<UserPublishDTO> getUserPublish(User user, Pageable pageable) {
        List<MyFriendDTO> friends = friendRepository.findByUserId(user.getId());
        List<Long> userIds = friends.stream().map(MyFriendDTO::getFriendId).collect(Collectors.toList());
        userIds.add(user.getId());
        List<UserPublishDTO> result = getUserPublish(userIds, pageable);
        return result;
    }

    /**
     * 通过用户  获取说说数量
     *
     * @param userId
     * @return
     */
    public Long countPublishCountByUserId(Long userId) {
        return publishCriticRepository.countByUserIdAndIsAllowTrue(userId);
    }

    /**
     * 发表说说
     *
     * @param critic
     * @param req
     */
    public void addPublishCritic(PublishCritic critic, MultipartFile file, HttpServletRequest req) {
        String context = critic.getCritic();
        String title = critic.getTitle();
        SensitivewordFilter.replaceSensitiveWord(context);
        SensitivewordFilter.replaceSensitiveWord(title);
        checkCritic(context, title);
        String save = "";
        String path = "";
        if (file!=null&&!file.isEmpty()) {
            String fileName = MyUtil.getInstance().getRandom() + System.currentTimeMillis() + ".jpg";
            File image = new File(filePath + fileName);
            if (!image.getParentFile().exists()) {
                image.mkdir();
            }

            try {
                file.transferTo(image);
            } catch (IOException e) {
                throw new ValidationException(ResultEnum.IMAGE_FAIL);
            }

            StringBuilder thumbnails = new StringBuilder();
            thumbnails.append(filePath);
            thumbnails.append("thumbnails/");
            thumbnails.append(fileName);
            // 色情图片识别
            if (!ImageRecognitionUtil.getInstance().checkPornograp(filePath + fileName)) {
                throw new ValidationException(ResultEnum.IMAGE_PORN);
            }
            if (MyUtil.getInstance().thumbnails(filePath + fileName, thumbnails.toString())) {
                save = filePath+"/thumbnails/" + fileName;
            } else {
                throw new ValidationException(ResultEnum.IMAGE_THUMBNAILS_FAIL);
            }
            path = filePath+fileName;
        }
        Optional<User> user = SecurityUtil.getLoginUser();

        publishCriticRepository.save(
                critic.setGood(0L)
                        .setIsAllow(true)
                        .setPicture(path)
                        .setThumbnails(save)
                        .setTime(LocalDateTime.now())
                        .setUserId(user.get().getId())
                        .setUpdateDate(LocalDateTime.now())
        );

    }

    /**
     *  检查说说
     * @param context
     * @param title
     */
    private void checkCritic(String context, String title) {
        if (context.length() < 1) {
            throw new ValidationException(ResultEnum.CONTEXT_NOT_EMPTY);
        }
        if (context.length() > 122) {
            throw new ValidationException(ResultEnum.CONTEXT_LENGTH);
        }
        if (title.length() < 1) {
            throw new ValidationException(ResultEnum.TITLE_NOT_EMPTY);
        }
        if (title.length() > 122) {
            throw new ValidationException(ResultEnum.TITLE_LENGTH);
        }
    }

    /**
     *  通过用户 查询用户发表的说说
     * @param userId
     * @param pageable
     */
    public List<UserPublishDTO> getUserPublishByuserId(Long userId, Pageable pageable) {
       return fillUserPublishDTO(publishCriticRepository.findByUserIdInOrderByUpdateDateDesc(Arrays.asList(userId),pageable).getContent());
    }

    /**
     *  获取热门说说
     * @param pageable
     * @return
     */
    public List<UserPublishDTO> getHotCritic(Pageable pageable) {
        return fillUserPublishDTO(publishCriticRepository.findByOrderByGoodDesc(pageable).getContent());
    }
}
