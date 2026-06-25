package com.yupi.yuaicodemother.core.saver;

import com.yupi.yuaicodemother.ai.model.HtmlCodeResult;
import com.yupi.yuaicodemother.exception.BusinessException;
import com.yupi.yuaicodemother.exception.ErrorCode;
import com.yupi.yuaicodemother.model.enums.CodeGenTypeEnum;
/**
 * HTML代码保存模板
 */
public class HtmlCodeFileSaverTemplate extends CodeFileSaverTemplate<HtmlCodeResult> {
    @Override
    protected void saveFiles(HtmlCodeResult result, String dirPath) {
        // 保存 HTML 代码
        writeToFile(dirPath, "index.html", result.getHtmlCode());

    }
    @Override
    protected CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.HTML;
    }
    @Override
    protected void validateInput(HtmlCodeResult result) {
        if (result == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "HTML代码结果对象不能为空");
        }
    }
}
