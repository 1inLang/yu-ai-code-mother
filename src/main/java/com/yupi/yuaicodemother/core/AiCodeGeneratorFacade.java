package com.yupi.yuaicodemother.core;

import com.yupi.yuaicodemother.ai.AiCodeGeneratorService;
import com.yupi.yuaicodemother.ai.model.HtmlCodeResult;
import com.yupi.yuaicodemother.ai.model.MultiFileCodeResult;
import com.yupi.yuaicodemother.core.parser.CodeParserExecutor;
import com.yupi.yuaicodemother.core.saver.CodeFileSaverExecutor;
import com.yupi.yuaicodemother.exception.BusinessException;
import com.yupi.yuaicodemother.exception.ErrorCode;
import com.yupi.yuaicodemother.model.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;

/**
 * AI代码生成器门面类
 */
@Service
@Slf4j
public class AiCodeGeneratorFacade {
    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;



    /**
     * 统一入口，根据类型生成并保存代码
     */
    public File generateAndSaveCode(String userMessage, CodeGenTypeEnum codeGenTypeEnum) {
        if(codeGenTypeEnum == null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"代码生成类型不能为null");
        return switch (codeGenTypeEnum) {
            case HTML -> {
                HtmlCodeResult result = aiCodeGeneratorService.generateHtmlCode(userMessage);
                yield CodeFileSaverExecutor.saveCode(result, codeGenTypeEnum);

            }
            case MULTI_FILE -> {
                MultiFileCodeResult result = aiCodeGeneratorService.generateMultiFileCode(userMessage);
                yield CodeFileSaverExecutor.saveCode(result, codeGenTypeEnum);

            }
            default -> {
                String message = "不支持的生成类型";
                throw new BusinessException(ErrorCode.PARAMS_ERROR,message);
            }
        };

    }
    /**
     * 统一入口，根据类型生成并保存代码(流式)
     */
    public Flux<String> generateAndSaveCodeStream(String userMessage, CodeGenTypeEnum codeGenTypeEnum) {
        return switch (codeGenTypeEnum) {
            case HTML -> {
                Flux<String> codeStream = aiCodeGeneratorService.generateHtmlCodeStream(userMessage);
                yield processCodeStream(codeStream, codeGenTypeEnum);
            }
            case MULTI_FILE -> {
                Flux<String> codeStream = aiCodeGeneratorService.generateMultiFileCodeStream(userMessage);
                yield processCodeStream(codeStream, codeGenTypeEnum);
            }
            default -> {
                String message = "不支持的生成类型";
                throw new BusinessException(ErrorCode.PARAMS_ERROR,message);
            }
        };
    }
    /**
     * 通用流式处理代码
     */
    private Flux<String> processCodeStream(Flux<String> codeStream, CodeGenTypeEnum codeGenTypeEnum) {
        StringBuilder codeBuilder = new StringBuilder();
        return codeStream.doOnNext(chunk -> {
            //实时处理代码
            codeBuilder.append(chunk);
        }).doOnComplete(() -> {
            //流式处理完成，保存代码
            try {
                String completeCode = codeBuilder.toString();
                //使用解析执行器解析代码
                Object result = CodeParserExecutor.parseCode(completeCode, codeGenTypeEnum);
                //使用保存执行器保存代码
                File file = CodeFileSaverExecutor.saveCode(result, codeGenTypeEnum);
            } catch (Exception e) {
                log.error("保存失败{}",e.getMessage());
            }
        });
    }
}
