// Imports the Google Cloud client library
const Translate = require('@google-cloud/translate');

// Instantiates a client
const translate = new Translate({
    key: '<REPLACE HERE GOOGLE_TRANSLATE_API_KEY>',
});

var fs = require("fs-extra");

const start = "{翻訳ここから}";
const end = "{ここまで翻訳}";

const languageList_Release = [
    "af",
    "sq",
    "am",
    "ar",
    "hy",
    "az",
    "eu",
    "be",
    "bn",
    "bs",
    "bg",
    "ca",
    "ceb",
    "zh-CN",
    "zh-TW",
    "co",
    "hr",
    "cs",
    "da",
    "nl",
    "en",
    "eo",
    "et",
    "fi",
    "fr",
    "fy",
    "gl",
    "ka",
    "de",
    "el",
    "gu",
    "ht",
    "ha",
    "haw",
    "iw",
    "hi",
    "hmn",
    "hu",
    "is",
    "ig",
    "id",
    "ga",
    "it",
    "ja",
    "jw",
    "kn",
    "kk",
    "km",
    "ko",
    "ku",
    "ky",
    "lo",
    "la",
    "lv",
    "lt",
    "lb",
    "mk",
    "mg",
    "ms",
    "ml",
    "mt",
    "mi",
    "mr",
    "mn",
    "my",
    "ne",
    "no",
    "ny",
    "ps",
    "fa",
    "pl",
    "pt",
    "pa",
    "ro",
    "ru",
    "sm",
    "gd",
    "sr",
    "st",
    "sn",
    "sd",
    "si",
    "sk",
    "sl",
    "so",
    "es",
    "su",
    "sw",
    "sv",
    "tl",
    "tg",
    "ta",
    "te",
    "th",
    "tr",
    "uk",
    "ur",
    "uz",
    "vi",
    "cy",
    "xh",
    "yi",
    "yo",
    "zu",
];

const languageList_Test = [
    "ar",
    "en",
    "ja",
    "ru",
];

const languageList = languageList_Test;

// const TARGET_DIR = "../Web/WEB-INF/template/translate-original/";
// const DEST_DIR = "../Web/WEB-INF/template/translate-converted/";
const TARGET_DIR = "../Web/WEB-INF/jsp/nammin/_original/";
const DEST_DIR = "../Web/WEB-INF/jsp/nammin/";

async function processDir(prefix) {
    let targetDir = TARGET_DIR + prefix;
    readdir(targetDir)
        .then(async files => {
            for(file of files.filter(function (file) {
                return fs.statSync(targetDir + file).isDirectory()
            })){
                await processDir(prefix + file  + "/");
            };
            files = files
                .filter(function (file) {
                    return fs.statSync(targetDir + file).isFile() && /.*\.(html|jsp)$/.test(targetDir + file);
                });
            for (const file of files) {
                await processFile(prefix,file);
            }
        })
}

const readFile = (path, opts = 'utf8') =>
    new Promise((res, rej) => {
        fs.readFile(path, opts, (err, data) => {
            if (err) rej(err)
            else res(data)
        })
    })

async function processFile(prefix,target) {
    let targetDir = TARGET_DIR + prefix;
    readFile(targetDir + target, "utf8").then(async data => {
        var translateStrings = [];
        data.split(start).slice(1).forEach(function (substr) {
            translateStrings.push(substr.split(end)[0])
        })
        translateStrings = translateStrings.filter(function (x, i, self) {
            return self.indexOf(x) === i;
        });
        console.log(targetDir + target + ": " + translateStrings);

        for (const lang of languageList) {
            let destDir = DEST_DIR + lang + "/" + prefix;
            fs.mkdirsSync(destDir);

            var resultText = data;
            for (const element of translateStrings) {
                const text = await translateText(element, lang)
                resultText = resultText.split(start + element + end).join(text);
            }
            fs.writeFileSync(destDir + target, resultText);
        }
    })
}

function readdir(path) {
    return new Promise(function (resolve, reject) {
        fs.readdir(path, function (error, result) {
            if (error) {
                reject(error);
            } else {
                resolve(result);
            }
        });
    });
}

async function translateText(text, lang) {
    if(lang === "ja"){
        return text;
    }
    return await
        translate
            .translate(text, lang)
            .then(results => {
                const translation = results[0];
                return translation;
            })
            .catch(err => {
                console.error('ERROR:', err);
            });
}



processDir("");