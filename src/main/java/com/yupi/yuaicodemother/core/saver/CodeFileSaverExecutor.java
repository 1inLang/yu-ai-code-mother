package com.yupi.yuaicodemother.core.saver;

import com.yupi.yuaicodemother.ai.model.HtmlCodeResult;
import com.yupi.yuaicodemother.ai.model.MultiFileCodeResult;
import com.yupi.yuaicodemother.model.enums.CodeGenTypeEnum;

import java.io.File;

/**
 * 代码保存器执行器
 *
 */
public class CodeFileSaverExecutor {
    private static final HtmlCodeFileSaverTemplate  htmlCodeFileSaverTemplate = new HtmlCodeFileSaverTemplate();
    private static final MultiFileCodeFileSaverTemplate multiFileCodeFileSaverTemplate = new MultiFileCodeFileSaverTemplate();
    /**
     * 保存代码
     *
     * @param codeResult 代码结果
     * @param codeGenTypeEnum 代码生成类型
     * @return 保存结果
     */
    public static File saveCode(Object codeResult, CodeGenTypeEnum codeGenTypeEnum) {
        return switch (codeGenTypeEnum) {
            case HTML -> htmlCodeFileSaverTemplate.saverCode((HtmlCodeResult) codeResult);
            case MULTI_FILE -> multiFileCodeFileSaverTemplate.saverCode((MultiFileCodeResult) codeResult);
            default -> throw new IllegalArgumentException("不支持的生成类型");
        };
    }
}
