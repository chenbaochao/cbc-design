/**
 * Created by Maibenben on 2017/6/3.
 */
/**
 * Created by Maibenben on 2017/5/1.
 */
$(function () {
    var start = 2;
    var now = false;

    $('.find_more').click(function () {
        getUP();
    });
    function getUP() {
        var uid = $('#userId').val();
        $.ajax({
            url: "/api/critic/"+uid,
            type: "GET",
            dataType: "JSON",
            data: {page: start},
            success: function (resp) {
                now = false;
                start++;
                var result = resp.data;
                var $all_stats = $('.friends_stats_fix');
                if (result.length === 0) {
                    $('.not_more').show(500);
                    $('.find_more').hide(500);
                    return;
                }
                var picture = "";
                var isPrivate = "";
                var action = "";
                var good = "";
                var collection = "";
                var uid = $('#uid').val();
                for (var i = 0; i < result.length; i++) {
                    if (result[i].thumbnails != null && result[i].thumbnails != "") {
                        picture = "<div class='friends_image'>" +
                            "<img class='friends_img_critic' src='" + result[i].thumbnails + "' " +
                            "alt='图片加载中......' title='" + result[i].title + "' value='" + result[i].picture + "'/>" +
                            "</div>";
                    } else {
                        if (result[i].picture != '' && result[i].picture != null) {
                            picture = "<div class='friends_image'>" +
                                "<img class='friends_img_critic' src='" + result[i].picture + "' " +
                                "alt='图片加载中......' title='" + result[i].title + "' value='" + result[i].picture + "'/>" +
                                "</div>";
                        }
                    }
                    if (result[i].isPrivate) {
                        isPrivate = "<span class='friends_time isPrivate'>朋友圈</span>";
                    } else {
                        isPrivate = "<span class='friends_time isPrivate'>公共圈</span>";
                    }
                    if (uid == result[i].userId) {
                        if (!result[i].isPrivate) {
                            action = "<div class='cbc'>" +
                                "<span class='show_action'>Ⅲ</span>" +
                                "<div class='show_action_menu'>" +
                                "<span class='action_menu' value='" + result[i].publishCriticId + "' title='translation'>转为朋友圈</span>" +
                                "<span class='action_menu' value='" + result[i].publishCriticId + "' title='delcritic'>×删除</span>" +
                                "</div>" +
                                "</div>";
                        } else {
                            action = "<div class='cbc'>" +
                                "<span class='show_action'>Ⅲ</span>" +
                                "<div class='show_action_menu'>" +
                                "<span class='action_menu' value='" + result[i].publishCriticId + "' title='delcritic'>×删除</span>" +
                                "</div>" +
                                "</div>";
                        }
                    }
                    if (result[i].isGood) {
                        good = "<button value='" + result[i].publishCriticId + "' class='friends_good_current'>♡已点赞 <span>" + result[i].goodCounts + "</span></button>";
                    } else {
                        good = "<button value='" + result[i].publishCriticId + "' class='friends_good'>♡点赞 <span>" + result[i].goodCounts + "</span></button>"
                    }
                    if (result[i].isCollection) {
                        collection = "<button value='" + result[i].publishCriticId + "' class='friends_collection_current'>☆已收藏 <span>" + result[i].collectionCounts + "</span></button>";
                    } else {
                        collection = "<button value='" + result[i].publishCriticId + "' class='friends_collection'>☆收藏 <span>" + result[i].collectionCounts + "</span></button>";
                    }
                    $all_stats.append("<div class='friends_stats'>" +
                        "<div>" +
                        "<div class='friends_stats_top'>" +
                        "<div>" +
                        "<img src='" + result[i].avatar + "' class='friends_image_header' alt='图片加载中.....' value='" + result[i].userId + "'/>" +
                        "</div>" +
                        "<div class='friends_name'>" +
                        "<div>" +
                        "<span  value='" + result[i].userId + "' class='friends_username'>" + result[i].nickname +
                        "</span>" +
                        "</div>" +
                        // "<br/>" +
                        "<span class='friends_time'>" + result[i].time +
                        "</span>" +
                        "<br/>" +
                        isPrivate +
                        "</div>" +
                        action +
                        "</div>" +
                        "<div class='friends_stats_middle clearfix'>" +
                        "<div class='friends_text'>" +
                        "<span>" + result[i].critic +
                        "</span>" +
                        "</div>" +
                        "<span class='friends_title'>" + "--" + result[i].title +
                        "</span>" +
                        "</div>" + picture +
                        "<div class='friends_action'>" +
                        collection +
                        "<button value='" + result[i].publishCriticId + "' class='friends_comment'  title='1'>评论 <span>" + result[i].commentCounts + "</span></button>" +
                        good +
                        "</div>" +
                        "</div>" +
                        "</div>"
                    );
                }
            },
            error:function () {
                layer.msg("请检查网络！！！");
            }
        });
    }
});