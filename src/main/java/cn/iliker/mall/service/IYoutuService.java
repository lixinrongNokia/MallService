package cn.iliker.mall.service;

import java.io.File;
import java.util.List;

/**
 * 腾讯优图图片智能识别服务
 * 包含（智能鉴黄）TODO
 */
public interface IYoutuService {
    /**
     * 智能鉴黄
     */
    void imageRecognition(List<File> files) throws Exception;
}
