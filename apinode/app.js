const { response } = require("express");
var express = require("express");
var app = express();

app.listen(3000, () => {
    console.log("Server running on port 3000");
});

app.get("/resposta-instantanea", (req, res, next) => {
    console.log("resposta-instantanea")
    res.status(200).send({
        "status": "ok",
        "statusCode": 200,
        "message": "sucesso"
    });
});

app.get("/resposta-assincrona", async (req, res, next) => {
    console.log("resposta-assincrona")
    await sleep(5000)
    res.status(200).send({
        "status": "ok",
        "statusCode": 200,
        "message": "sucesso"
    });
});

app.get("/resposta-erro", (req, res, next) => {
    console.log("resposta-erro")
    res.status(500).send({
        "status": "error",
        "statusCode": 500,
        "message": "falha"
    });
});

var sleep = (ms) => {
    return new Promise((resolve) => {
        setTimeout(resolve, ms);
    });
}