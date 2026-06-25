package com.yupi.yuaicodemother.core.parser;

import com.yupi.yuaicodemother.model.enums.CodeGenTypeEnum;

/**
 * 代码解析器执行器
 *
 */
public class CodeParserExecutor {
    /**
     * 解析代码
     *
     * @param codeContent 代码内容
     * @param codeGenTypeEnum 代码生成类型
     * @return 解析结果
     */
    public static Object parseCode(String codeContent, CodeGenTypeEnum codeGenTypeEnum) {
        return switch (codeGenTypeEnum) {
            case HTML -> new HtmlCodeParser().parseCode(codeContent);
            case MULTI_FILE -> new MultiFileCodeParser().parseCode(codeContent);
            default -> throw new IllegalArgumentException("不支持的生成类型");
        };
    }

}
