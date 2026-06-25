package com.yupi.yuaicodemother.core.saver;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.yupi.yuaicodemother.exception.BusinessException;
import com.yupi.yuaicodemother.exception.ErrorCode;
import com.yupi.yuaicodemother.model.enums.CodeGenTypeEnum;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * 代码保存模板
 *
 * @param <T>
 */
public abstract class CodeFileSaverTemplate<T> {
    /**
     * 文件保存的根目录
     */
    private static final String FILE_SAVE_ROOT_DIR = System.getProperty("user.dir") + "/tmp/code_output";
   public final File saverCode(T result){
       //1.验证输入
       validateInput(result);
       //2.构建唯一文件路径
       String dirPath = buildUniqueDir();
       //3.保存文件(交给子类)
       saveFiles(result,dirPath);
       //4.返回文件目录对象
       return new File(dirPath);
   }
   protected void validateInput(T result){
        if(result == null)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "代码结果对象不能为空");
        }
   }
    protected final void writeToFile(String dirPath, String filename, String content) {
        String filePath = dirPath + File.separator + filename;
        FileUtil.writeString(content, filePath, StandardCharsets.UTF_8);
    }
    /**
     * 构建文件的唯一路径：tmp/code_output/bizType_雪花 ID
     *
     * @return
     */
    private  String buildUniqueDir() {
        String codeType = getCodeType().getValue();
        String uniqueDirName = StrUtil.format("{}_{}", codeType, IdUtil.getSnowflakeNextIdStr());
        String dirPath = FILE_SAVE_ROOT_DIR + File.separator + uniqueDirName;
        FileUtil.mkdir(dirPath);
        return dirPath;
    }
    protected abstract void saveFiles(T result, String dirPath);
    protected abstract CodeGenTypeEnum getCodeType();

}
