package com.cbc.design.common;

public class BaiduExceptionMatch {


    public static String match(String msg){

        String result = "";
        switch(msg.trim()){
            case "Open api request limit reached":
                result = "集群超限额"; break;
            case "IAM Certification failed":
                result = "IAM鉴权失败，建议用户参照文档自查生成sign的方式是否正确，或换用控制台中ak sk的方式调用"; break;
            case "Open api daily request limit reached":
                result = "每天流量超限额"; break;
            case "Open api qps request limit reached":
                result = "QPS超限额"; break;
            case "Open api total request limit reached":
                result = "请求总量超限额"; break;
            case "Invalid parameter":
                result = "无效参数"; break;
            case "Access token invalid or no longer valid":
                result = "Access Token失效"; break;
            case "Access token expired":
                result = "Access token过期"; break;
            case "param[] is null":
                result = "必要参数未传入"; break;
            case "param[start] format error":
                result = "参数格式错误"; break;
            case "param[length] format error":
                result = "参数格式错误"; break;
            case "param[op_app_id_list] format error":
                result = "参数格式错误"; break;
            case "param[group_id_list] format error":
                result = "参数格式错误"; break;
            case "group_id format error":
                result = "用户组id格式错误"; break;
            case "uid format error":
                result = "用户id格式错误"; break;
            case "face_id format error":
                result = "人脸格式错误"; break;
            case "user_info format error":
                result = "用户信息格式错误"; break;
            case "param[uid_list] format error":
                result = "参数格式错误"; break;
            case "param[image] format error":
                result = "人脸检测格式错误"; break;
            case "network not available":
                result = "服务端请求失败"; break;
            case "pic not has face":
                result = "没有检测到人脸"; break;
            case "image check fail":
                result = "无法解析人脸"; break;
            case "rtse service return fail":
                result = "服务端请求失败"; break;
            case "match user is not found":
                result = "未找到匹配的用户"; break;
            case "face token not exist":
                result = "face token不存在"; break;
            case "add face fail":
                result = "人脸添加失败"; break;
            case "group is not exist":
                result = "操作的用户组不存在"; break;
            case "group is already exist":
                result = "该用户组已存在"; break;
            case "user is already exist":
                result = "该用户已存在"; break;
            case "user is not exist":
                result = "找不到该用户"; break;
            case "group_list is too large":
                result = "group_list包含组数量过多"; break;
            case "face is already exist":
                result = "该人脸已存在"; break;
            case "face is not exist":
                result = "该人脸不存在"; break;
            case "uid_list is too large":
                result = "uid_list包含数量过多"; break;
            case "dst group is not exist":
                result = "目标用户组不存在"; break;
            case "quality_conf format error":
                result = "quality_conf格式不正确"; break;
            case "face is covered":
                result = "人脸有被遮挡"; break;
            case "face is fuzzy":
                result = "人脸模糊"; break;
            case "face light is not good":
                result = "人脸光照不好"; break;
            case "incomplete face":
                result = "人脸不完整"; break;
            case "app_list is too large":
                result = "app_list包含app数量过多"; break;
            case "quality control error":
                result = "质量控制项错误"; break;
            case "liveness control item error":
                result = "活体控制项错误"; break;
            case "liveness check fail":
                result = "活体检测未通过"; break;
            case "left eye is occlusion":
                result = "质量检测未通过 左眼遮挡程度过高"; break;
            case "right eye is occlusion":
                result = "质量检测未通过 右眼遮挡程度过高"; break;
            case "left cheek is occlusion":
                result = "质量检测未通过 左脸遮挡程度过高"; break;
            case "right cheek is occlusion":
                result = "质量检测未通过 右脸遮挡程度过高"; break;
            case "chin contour is occlusion":
                result = "质量检测未通过 下巴遮挡程度过高"; break;
            case "nose is occlusion":
                result = "质量检测未通过 鼻子遮挡程度过高"; break;
            case "mouth is occlusion":
                result = "质量检测未通过 嘴巴遮挡程度过高"; break;
            case "police picture is none or low quality":
                result = "公安网图片不存在或质量过低"; break;
            case "id number and name not match or id number not exist":
                result = "身份证号与姓名不匹配或该身份证号不存在"; break;
            case "name format error":
                result = "身份证名字格式错误"; break;
            default:
                result = msg.trim();
        }
        return result;
    }

}
