package net.hitachifbbot.utils;

import net.hitachifbbot.model.DoctorUserData;
import net.hitachifbbot.model.NamminUserData;
import net.hitachifbbot.session.AppSession;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.VariablesMap;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Template {

    private static final String TEMPLATE_ROOT_PATH = "/WEB-INF/template/";
    private static final String TRANSLATED_TEMPLATE_ROOT_PATH = "/WEB-INF/template/translate-converted/";

    private static final String DEFAULT_LANGUAGE = "en";

    private static final String TEMPLATE_SUFFIX = ".html";

    private static HashSet<String> convertedLangCodes = null;

    /**
     * 変換されている可能性がある言語コード一覧を取得します。
     * @param req
     * @return
     */
    private static synchronized HashSet<String> getTemplateConvertedLangCodes(HttpServletRequest req){
        if(convertedLangCodes != null){
            return convertedLangCodes;
        }
        convertedLangCodes = new HashSet<>();
        File baseDir = new File(req.getServletContext().getRealPath(TRANSLATED_TEMPLATE_ROOT_PATH));
        for (File child : baseDir.listFiles()) {
            if(child.isDirectory() && !child.getName().startsWith("_")){
                convertedLangCodes.add(child.getName());
            }
        }
        return convertedLangCodes;
    }

    /**
     * 翻訳されたテンプレートを返す
     * @param templateName
     * @param map
     * @param req
     * @param resp
     * @throws IOException
     */
    public static void responseTranslated(String templateName, HashMap<String,Object> map, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String templateLangCode = DEFAULT_LANGUAGE; // デフォルトは英語
        String userLangCode = DEFAULT_LANGUAGE;

        // ユーザーの希望言語を取得
        AppSession.UserData userData = AppSession.getUserData(req);
        if (userData instanceof DoctorUserData){
            userLangCode = ((DoctorUserData) userData).getDbUserData().translateLangCode;
        }else if(userData instanceof NamminUserData){
            userLangCode = ((NamminUserData) userData).getDbUserData().translateLangCode;
        }

        // 希望言語が存在したらtemplateLangCodeに設定
        if (getTemplateConvertedLangCodes(req).contains(userLangCode)) {
            // 翻訳済みのものはあるか？
            String templateFilePath = req.getServletContext().getRealPath(TRANSLATED_TEMPLATE_ROOT_PATH + userLangCode + "/" + templateName + TEMPLATE_SUFFIX);
            if(new File(templateFilePath).exists()){ // 翻訳済みのものが有った
                templateLangCode = userLangCode;
            }
        }

        resp.setContentType("text/html;charset=UTF-8");

        final TemplateEngine engine = initializeTemplateEngine(TRANSLATED_TEMPLATE_ROOT_PATH + templateLangCode + "/");
        IContext ctx = new WebContext(req,resp,req.getServletContext());

        final VariablesMap<String, Object> variablesMap = ctx.getVariables();
        for (Map.Entry<String,Object> entry : map.entrySet()) {
            variablesMap.put(entry.getKey(), entry.getValue());
        }

        engine.process(templateName, ctx, resp.getWriter());
    }

    public static void response(String templateName, HashMap<String,Object> map, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        final TemplateEngine engine = initializeTemplateEngine(TEMPLATE_ROOT_PATH);
        IContext ctx = new WebContext(req,resp,req.getServletContext());

        final VariablesMap<String, Object> variablesMap = ctx.getVariables();
        for (Map.Entry<String,Object> entry : map.entrySet()) {
            variablesMap.put(entry.getKey(), entry.getValue());
        }

        engine.process(templateName, ctx, resp.getWriter());
    }

    /**
     * TemplateEngineを生成する。
     * 本来ココはstaticに保持するなりして使いまわせるはず。
     * @return TemplateEngine
     */
    private static TemplateEngine initializeTemplateEngine(String templateDir) {
        final TemplateEngine templateEngine = new TemplateEngine();
        final TemplateResolver resolver = new ServletContextTemplateResolver();

        resolver.setTemplateMode("HTML5");
        // クラスパスのtemplatesディレクトリ配下にテンプレートファイルを置くことにする
        resolver.setPrefix(templateDir);
        resolver.setSuffix(TEMPLATE_SUFFIX);
        resolver.setCharacterEncoding("UTF-8");
        templateEngine.setTemplateResolver(resolver);
        return templateEngine;
    }
}
