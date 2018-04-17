/**
 * Created by Maibenben on 2017/5/1.
 */
$(function () {
    var start = 1;
    var now = false;
    // var to_top = $('.go_to_top');
    // $(to_top).click(function () {
    //     $("body,html").animate({scrollTop: 0}, 800);
    // });
    var num = 0.75;
    //自动加载
    $(window).scroll(function () {
        var sc = $(window).scrollTop();
        var height = $('.all_content').height();
        if (sc > height * num) {
            if (!now) {
                now = true;
                getUP();
                if (num < 0.95) {
                    num += 0.05;
                }
            }
        }
    });
//显示图片
    $("#file_upload").change(function () {
        var $file = $(this);
        var fileObj = $file[0];
        var windowURL = window.URL || window.webkitURL;
        var dataURL;
        var $img = $("#preview");

        if (fileObj && fileObj.files && fileObj.files[0]) {
            dataURL = windowURL.createObjectURL(fileObj.files[0]);
            $img.attr('src', dataURL);
        } else {
            dataURL = $file.val();
            var imgObj = document.getElementById("preview");
// 两个坑:
// 1、在设置filter属性时，元素必须已经存在在DOM树中，动态创建的Node，也需要在设置属性前加入到DOM中，先设置属性在加入，无效；
// 2、src属性需要像下面的方式添加，上面的两种方式添加，无效；
            imgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
            imgObj.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dataURL;
        }
        $('.image_container').show(100);
    });
    $('.close_img_tip').click(function () {
        $('.image_container').hide(100);
        $('#preview').attr('src', '');
        $('#file_upload').val('');
    });
    // var $movie_name = $('.movie_name_text');
    var $movie_content = $('.release_message');

    $('.find_more').click(function () {
        getUP();
    });

    //向下滚动时，自动加载
    function getUP() {
        $.ajax({
            url: "/api/critic/getUP",
            type: "GET",
            dataType: "JSON",
            data: {page: start},
            success: function (resp) {
                now = false;
                start++;
                var result = resp.data;
                console.log(result.length)
                var $all_stats = $('.friends_stats_fix');
                if (result==null||result.length==0) {
                    $('.not_more').show();
                    $('.find_more').hide();
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
                        if (result[i].isPrivate) {
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
                    } else {
                        action = "<div class='cbc'>" +
                            "<span class='show_action'>Ⅲ</span>" +
                            "<div class='show_action_menu'>" +
                            "<span class='action_menu' value='" + result[i].publishCriticId + "' title='unsubscribe'>×关注</span>" +
                            "<span class='action_menu' value='" + result[i].publishCriticId + "' title='report'>●举报</span>" +
                            "</div>" +
                            "</div>";
                    }
                    if (result[i].isGood) {
                        good = "<button value='" + result[i].publishCriticId + "' class='friends_good_current'>♡点赞 <span>" + result[i].goodCounts + "</span></button>";
                    } else {
                        good = "<button value='" + result[i].publishCriticId + "' class='friends_good'>♡点赞 <span>" + result[i].goodCounts + "</span></button>"
                    }
                    if (result[i].isCollection) {
                        collection = "<button value='" + result[i].publishCriticId + "' class='friends_collection_current'>☆收藏 <span>" + result[i].collectionCounts + "</span></button>";
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
                        "<span  value='" + result[i].uid + "' class='friends_username'>" + result[i].nickname +
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
                        "<button value='" + result[i].publishCriticId + "' class='friends_comment' title='1'>◇评论 <span>" + result[i].commentCounts + "</span></button>" +
                        good +
                        "</div>" +
                        "</div>" +
                        "</div>"
                    );
                }
                // showPicture();
            }
        });
    }

    var $size_tip = $('.size_tip');
    $movie_content.bind('input propertychange', function () {
        var curLength = $(this).val().length;
        $size_tip.text("还可以输入" + (122 - curLength) + "个字");
        if (curLength >= 122) {
            var num = $(this).val().substr(0, 121);
            $(this).val(num);
            layer.msg("超过字数限制，多出的字将被截断！");
        }
        if (curLength > 0) {
            $(".movie_release").addClass('chang_btn');
        } else {
            $(".movie_release").removeClass('chang_btn');
        }
    });
});
//发表影评
$(document).on("click", ".chang_btn", function () {
    var $title = $('.movie_name_text');
    var $critic = $('.release_message');
    var $isPrivate = $('.movie_state');
    if ($title.val().length < 1) {
        layer.msg("标题不能为空");
        return;
    }
    if ($critic.val().length < 1) {
        layer.msg("发布的内容不能为空");
        return;
    }
    var imgPath = $('#file_upload').val();

    var $username = $(".myName").attr("title");//用户名
    var $userImage = $(".my_image_header").attr("src");//用户头像
    var $userId = $("#myId").val();//用户ID
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hours = date.getHours();
    var min = date.getMinutes();
    var seconds = date.getSeconds();
    var nowTime = year + "-" + month + "-" + day + " " + hours + ":" + min + ":" + seconds;
//        alert(imgPath);
    //没有图片
    if (imgPath == "") {
        //先将发表的内容显示出来
        // alert("我要开始发送啦");
        var isPri = "<span class='friends_time isPrivate' \" +\n" +
            "                \"value='true'>朋友圈</span>";
        if ($isPrivate.val()) {
            isPri = "<span class='friends_time isPrivate' " +
                "value='true'>朋友圈</span>";
        } else {
            isPri = "<span class='friends_time isPrivate' " +
                "value='false'>公共圈</span>";
        }
        var show = "<div class='friends_stats my_stats' style='display: none'>" +
            "                    <div>" +
            "                        <div class='friends_stats_top'>" +
            "                            <div>" +
            "                                <img src='" + $userImage + "' class='friends_image_header' alt='??'" +
            "                                     value='" + $userId + "'/>" +
            "                            </div>" +
            "                            <div class='friends_name'>" +
            "                                <div>" +
            "                                    <span value='" + $userId + "'" +
            "                                          class='friends_username'>" + $username + "</span>" +
            "                                </div>" +
            "                                <span class='friends_time' " +
            "                                      '>" + nowTime + "</span>" +
            "                                <br/>" + isPri +
            "                            </div>" +
            "                            <div class='cbc' >" +
            "                                <span class='show_action'>Ⅲ</span>" +
            "                                <div  class='show_action_menu' >" +
            "                                </div>" +
            "                            </div>" +
            "                        </div>" +
            "                        <div class='friends_stats_middle clearfix'>" +
            "                            <div class='friends_text'>" +
            "                            <span >" + $critic.val() +
            "                            </span>" +
            "                            </div>" +
            "                            <span class='friends_title'>" + $title.val() +
            "                        </span>" +
            "                        </div>" +
            "                        <div class='friends_action'>" +
            "                            <button class='friends_collection'>" +
            "                                ☆收藏" +
            "                                <span>0</span>" +
            "                            </button>" +
            "                            <button  class='friends_comment' title='1'>" +
            "                                ◇评论" +
            "                                <span>0</span>" +
            "                            </button>" +
            "                            <button  class='friends_good'>" +
            "                                ♡点赞" +
            "                                <span >0</span>" +
            "                            </button>" +
            "                        </div>" +
            "                    </div>" +
            "                </div>";
        var friends_stats_fix = $(".friends_stats_fix");
        friends_stats_fix.prepend(show);
        $(".my_stats").show(500);
        $.ajax({
            type: "post",
            url: "/api/critic/publish/critic",
            dataType: "JSON",
            data: {title: $title.val(), critic: $critic.val(), isPrivate: $isPrivate.val()},//一同上传的数据
            success: function (data) {
                if(200!=data.code){
                    layer.msg(data.msg);
                }
            },
            error: function () {
            }

        });
    }
    else {
        // alert("我要开始发送啦，有图片");
        var formData = new FormData();
        // var name = $("#file_upload").val();
        var imageSrc = $("#preview").attr('src');
        formData.append("file_upload", $("#file_upload")[0].files[0]);
        formData.append("title", $title.val());
        formData.append("critic", $critic.val());
        formData.append("isPrivate", $isPrivate.val());
        // formData.append("name",name);
        //发表的内容显示出来
        //先将发表的内容显示出来
        // alert("我要开始发送啦");
        var isPri = "<span class='friends_time isPrivate' \" +\n" +
            "                \"value='true'>朋友圈</span>";
        if ($isPrivate.val()) {
            isPri = "<span class='friends_time isPrivate' " +
                "value='true'>朋友圈</span>";
        } else {
            isPri = "<span class='friends_time isPrivate' " +
                "value='false'>公共圈</span>";
        }
        var show = "<div class='friends_stats my_stats' style='display: none'>" +
            "                    <div>" +
            "                        <div class='friends_stats_top'>" +
            "                            <div>" +
            "                                <img src='" + $userImage + "' class='friends_image_header' alt='??'" +
            "                                     value='" + $userId + "'/>" +
            "                            </div>" +
            "                            <div class='friends_name'>" +
            "                                <div>" +
            "                                    <span value='" + $userId + "'" +
            "                                          class='friends_username'>" + $username + "</span>" +
            "                                </div>" +
            "                                <span class='friends_time' " +
            "                                      '>" + nowTime + "</span>" +
            "                                <br/>" + isPri +
            "                            </div>" +
            "                            <div class='cbc' >" +
            "                                <span class='show_action'>Ⅲ</span>" +
            "                                <div  class='show_action_menu' >" +
            "                                </div>" +
            "                            </div>" +
            "                        </div>" +
            "                        <div class='friends_stats_middle clearfix'>" +
            "                            <div class='friends_text'>" +
            "                            <span >" + $critic.val() +
            "                            </span>" +
            "                            </div>" +
            "                            <span class='friends_title'>" + $title.val() +
            "                        </span>" +
            "                        </div>" +
            "<div class='friends_image' >" +
            "                            <img class='friends_img_critic' src='" + imageSrc + "'" +
            "                                 alt='" + $title.val() + "' title='" + $title.val() + "' value = '" + imageSrc + "'/>" +
            "                        </div>" +
            "                        <div class='friends_action'>" +
            "                            <button class='friends_collection'>" +
            "                                ☆收藏" +
            "                                <span>0</span>" +
            "                            </button>" +
            "                            <button  class='friends_comment' title='1'>" +
            "                                ◇评论" +
            "                                <span>0</span>" +
            "                            </button>" +
            "                            <button  class='friends_good'>" +
            "                                ♡点赞" +
            "                                <span >0</span>" +
            "                            </button>" +
            "                        </div>" +
            "                    </div>" +
            "                </div>";
        var friends_stats_fix = $(".friends_stats_fix");
        friends_stats_fix.prepend(show);
        $(".my_stats").show(500);
        $.ajax({
            type: "POST",
            url: "/api/critic/publish/critic",
            contentType: false,
            dataType: 'json',//返回数据的类型
            processData: false,
            data: formData,//一同上传的数据
            success: function (resp) {
                if (200 != resp.code) {
                    layer.msg(resp.msg);
                }
//                        $('.img').attr('src', msg.url);
            },
            error: function () {
                layer.msg("上传失败，请检查网络后重试");
            }
        });
    }
});