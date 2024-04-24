import { check } from 'k6';
import http from 'k6/http';

export const options = {
    scenarios: {
        my_scenario1: {
            executor: 'constant-arrival-rate',
            duration: '60s', // total duration
            preAllocatedVUs: 1000, // to allocate runtime resources

            rate: 1000, // number of constant iterations given `timeUnit`
            timeUnit: '1s',
        },
    },
};

export default function () {
    const payload = JSON.stringify({
        "hr_tran":"14:06:02",
        "apelido":"",
        "latitude":"",
        "gbIDH":"2007C4FE7B9C1C23",
        "tip_pss_dst":1.0,
        "cd_tran_atc":"0000000000000049988740001",
        "in_cnfc":0.0,
        "nr_ct_dst":270075.0,
        "cdTrfPtac":"17",
        "vl_tran":2.96,
        "versaoAplicativo":"",
        "tip_ct_dst":"1",
        "cdSeqlPtac":"280007",
        "marca":"",
        "versaoGB":"1.3.4.5",
        "aplicativo":"",
        "cd_tran":"PI1",
        "browser":"FF82.",
        "ispb_dst":0.0,
        "id":"",
        "so":"MAC00",
        "gbCorMaquina":"2",
        "nm_so":"",
        "cpf_cnpj_dst":4.8600389836E10,
        "cd_cnl":12.0,
        "porta":55508.0,
        "nsuAtc":"9317280007",
        "longitude":"",
        "cd_ag_ogm":551.0,
        "nm_dst":"ARNALDO QUEIROZ CUNHA",
        "ip":"172.18.88.22",
        "dt_tran":"2023-01-09",
        "cd_mci":1.3697565E7,
        "idh":"",
        "tx_msg_pgdr":"WEB - APF - Pagar com Pix - Pagar ou transferir com Pix - Dados da conta - efetuar pagamento informando os dados bancarios manualmente - Conta corrente",
        "modelo":"",
        "cpf_cnpj_ogm":6.1190229889E10,
        "cd_ag_dst":551.0,
        "cdMbrPtac":"93",
        "imei":"",
        "tip_pss_ogm":1.0,
        "nr_ct_ogm":30281.0,
        "debug_hash":{
           "hostname":"sgn-100-score-pix-mensageria-saida-5dd75dddd5-rzdwb",
           "timestampCriacaoHash":"17/04/2024 10:04:01:511",
           "entrada":"17/04/2024 10:04:01:881",
           "idServico":"100",
           "saida":"17/04/2024 10:04:01:881",
           "nomeConfiguracao":"score-pix",
           "destino":"BB.BSB.PB.GRAVA.ESTATISTICA",
           "componente":"sgn-mensageria-saida",
           "hash":"349655dbfe353db28febb824f8149fe3",
           "idServicoPai":"11541"
        },
        "debug_11369_FiltroTransacaoPixPI1":{
           "hostname":"sgn-11369-filtrotransacaopixpi1-mensageria-67f84c4889-k6whq",
           "timestampCriacaoHash":"17/04/2024 10:04:01:511",
           "entrada":"17/04/2024 10:04:01:511",
           "idServico":"11369",
           "nomeConfiguracao":"FiltroTransacaoPixPI1",
           "destino":"BB.BSB.JEE.PBL.PB.$40.ACR.ACR.0.0.5022778.1",
           "hash":"349655dbfe353db28febb824f8149fe3",
           "idServicoPai":0.0,
           "sgn-mensageria-entrada":{
              "hostname":"sgn-11369-filtrotransacaopixpi1-mensageria-67f84c4889-k6whq",
              "inicio":"17/04/2024 10:04:01:511",
              "fim":"17/04/2024 10:04:01:513"
           },
           "sgn-filtro-filtro":{
              "hostname":"sgn-11369-filtrotransacaopixpi1-filtro-667b679667-k96jk",
              "inicio":"17/04/2024 10:04:01:549",
              "fim":"17/04/2024 10:04:01:561"
           },
           "sgn-mapa-mensagem":{
              "hostname":"sgn-11369-filtrotransacaopixpi1-mapa-5f5d655f78-5zdfx",
              "inicio":"17/04/2024 10:04:01:591",
              "fim":"17/04/2024 10:04:01:602"
           },
           "sgn-mensageria-saida":{
              "hostname":"sgn-11369-filtrotransacaopixpi1-mensageria-saida-7777d6976z5tbv",
              "inicio":"17/04/2024 10:04:01:627",
              "fim":"17/04/2024 10:04:01:627"
           },
           "saida":"17/04/2024 10:04:01:628"
        },
        "regra":"16718-gri",
        "MotivoExclusaoRegra":"DescartarAntesDaPesquisa",
        "cdPtac":"05",
        "vlPtac":"1",
        "debug_11541_16718-gri":{
           "hostname":"sgn-11541-16718-gri-mensageria-5ccdbfb4dd-d65xf",
           "entrada":"17/04/2024 10:04:01:646",
           "idServico":"11541",
           "nomeConfiguracao":"16718-gri",
           "destino":"BB.BSB.PB.ACR.PI1.GERAL.FILTRO",
           "idServicoPai":"11369",
           "sgn-mensageria-entrada":{
              "hostname":"sgn-11541-16718-gri-mensageria-5ccdbfb4dd-d65xf",
              "inicio":"17/04/2024 10:04:01:646",
              "fim":"17/04/2024 10:04:01:647"
           },
           "sgn-filtro-regra":{
              "hostname":"sgn-11541-16718-gri-filtro-78bb4c6f5d-r6t8l",
              "inicio":"17/04/2024 10:04:01:312",
              "fim":"17/04/2024 10:04:01:323"
           },
           "sgn-mensageria-saida":{
              "hostname":"sgn-11541-16718-gri-mensageria-saida-7b77d8c747-clk4w",
              "inicio":"17/04/2024 10:04:01:688",
              "fim":"17/04/2024 10:04:01:688"
           },
           "saida":"17/04/2024 10:04:01:689"
        },
        "timestampSGN_OUT":"2024-04-17T10:04:01.882",
        "respostaVSAM":"{\"codigoRetorno\":197,\"codigoSequencialRetorno\":6,\"textoRetorno\":\"TRANSACAO NAO DISPONIVEL\"}",
        "debug_100_score-pix":{
           "hostname":"sgn-100-score-pix-mensageria-6d9c9f8b5d-s5mxs",
           "entrada":"17/04/2024 10:04:01:703",
           "idServico":"100",
           "nomeConfiguracao":"score-pix",
           "destino":"BB.BSB.PB.SCORE.VSAM",
           "idServicoPai":"11541",
           "sgn-mensageria-entrada":{
              "hostname":"sgn-100-score-pix-mensageria-6d9c9f8b5d-s5mxs",
              "inicio":"17/04/2024 10:04:01:702",
              "fim":"17/04/2024 10:04:01:704"
           },
           "score":{
              "hostname":"sgn-100-score-pix-score-859c9c6c4c-tjswq",
              "inicio":"17/04/2024 10:04:01:794",
              "fim":"17/04/2024 10:04:01:859"
           },
           "sgn-mensageria-saida":{
              "hostname":"sgn-100-score-pix-mensageria-saida-5dd75dddd5-rzdwb",
              "inicio":"17/04/2024 10:04:01:881",
              "fim":"17/04/2024 10:04:01:881"
           },
           "saida":"17/04/2024 10:04:01:882"
        },
        "hr_tran":"14:06:02",
        "apelido":"",
        "latitude":"",
        "gbIDH":"2007C4FE7B9C1C23",
        "tip_pss_dst":1.0,
        "cd_tran_atc":"0000000000000049988740001",
        "in_cnfc":0.0,
        "nr_ct_dst":270075.0,
        "cdTrfPtac":"17",
        "vl_tran":2.96,
        "versaoAplicativo":"",
        "tip_ct_dst":"1",
        "cdSeqlPtac":"280007",
        "marca":"",
        "versaoGB":"1.3.4.5",
        "aplicativo":"",
        "cd_tran":"PI1",
        "browser":"FF82.",
        "ispb_dst":0.0,
        "id":"",
        "so":"MAC00",
        "gbCorMaquina":"2",
        "nm_so":"",
        "cpf_cnpj_dst":4.8600389836E10,
        "cd_cnl":12.0,
        "porta":55508.0,
        "nsuAtc":"9317280007",
        "longitude":"",
        "cd_ag_ogm":551.0,
        "nm_dst":"ARNALDO QUEIROZ CUNHA",
        "ip":"172.18.88.22",
        "dt_tran":"2023-01-09",
        "cd_mci":1.3697565E7,
        "idh":"",
        "tx_msg_pgdr":"WEB - APF - Pagar com Pix - Pagar ou transferir com Pix - Dados da conta - efetuar pagamento informando os dados bancarios manualmente - Conta corrente",
        "modelo":"",
        "cpf_cnpj_ogm":6.1190229889E10,
        "cd_ag_dst":551.0,
        "cdMbrPtac":"93",
        "imei":"",
        "tip_pss_ogm":1.0,
        "nr_ct_ogm":30281.0,
        "debug_hash":{
           "hostname":"sgn-100-score-pix-mensageria-saida-5dd75dddd5-rzdwb",
           "timestampCriacaoHash":"17/04/2024 10:04:01:511",
           "entrada":"17/04/2024 10:04:01:881",
           "idServico":"100",
           "saida":"17/04/2024 10:04:01:881",
           "nomeConfiguracao":"score-pix",
           "destino":"BB.BSB.PB.GRAVA.ESTATISTICA",
           "componente":"sgn-mensageria-saida",
           "hash":"349655dbfe353db28febb824f8149fe3",
           "idServicoPai":"11541"
        },
        "debug_11369_FiltroTransacaoPixPI1":{
           "hostname":"sgn-11369-filtrotransacaopixpi1-mensageria-67f84c4889-k6whq",
           "timestampCriacaoHash":"17/04/2024 10:04:01:511",
           "entrada":"17/04/2024 10:04:01:511",
           "idServico":"11369",
           "nomeConfiguracao":"FiltroTransacaoPixPI1",
           "destino":"BB.BSB.JEE.PBL.PB.$40.ACR.ACR.0.0.5022778.1",
           "hash":"349655dbfe353db28febb824f8149fe3",
           "idServicoPai":0.0,
           "sgn-mensageria-entrada":{
              "hostname":"sgn-11369-filtrotransacaopixpi1-mensageria-67f84c4889-k6whq",
              "inicio":"17/04/2024 10:04:01:511",
              "fim":"17/04/2024 10:04:01:513"
           },
           "sgn-filtro-filtro":{
              "hostname":"sgn-11369-filtrotransacaopixpi1-filtro-667b679667-k96jk",
              "inicio":"17/04/2024 10:04:01:549",
              "fim":"17/04/2024 10:04:01:561"
           },
           "sgn-mapa-mensagem":{
              "hostname":"sgn-11369-filtrotransacaopixpi1-mapa-5f5d655f78-5zdfx",
              "inicio":"17/04/2024 10:04:01:591",
              "fim":"17/04/2024 10:04:01:602"
           },
           "sgn-mensageria-saida":{
              "hostname":"sgn-11369-filtrotransacaopixpi1-mensageria-saida-7777d6976z5tbv",
              "inicio":"17/04/2024 10:04:01:627",
              "fim":"17/04/2024 10:04:01:627"
           },
           "saida":"17/04/2024 10:04:01:628"
        },
        "regra":"16718-gri",
        "MotivoExclusaoRegra":"DescartarAntesDaPesquisa",
        "cdPtac":"05",
        "vlPtac":"1",
        "debug_11541_16718-gri":{
           "hostname":"sgn-11541-16718-gri-mensageria-5ccdbfb4dd-d65xf",
           "entrada":"17/04/2024 10:04:01:646",
           "idServico":"11541",
           "nomeConfiguracao":"16718-gri",
           "destino":"BB.BSB.PB.ACR.PI1.GERAL.FILTRO",
           "idServicoPai":"11369",
           "sgn-mensageria-entrada":{
              "hostname":"sgn-11541-16718-gri-mensageria-5ccdbfb4dd-d65xf",
              "inicio":"17/04/2024 10:04:01:646",
              "fim":"17/04/2024 10:04:01:647"
           },
           "sgn-filtro-regra":{
              "hostname":"sgn-11541-16718-gri-filtro-78bb4c6f5d-r6t8l",
              "inicio":"17/04/2024 10:04:01:312",
              "fim":"17/04/2024 10:04:01:323"
           },
           "sgn-mensageria-saida":{
              "hostname":"sgn-11541-16718-gri-mensageria-saida-7b77d8c747-clk4w",
              "inicio":"17/04/2024 10:04:01:688",
              "fim":"17/04/2024 10:04:01:688"
           },
           "saida":"17/04/2024 10:04:01:689"
        },
        "timestampSGN_OUT":"2024-04-17T10:04:01.882",
        "respostaVSAM":"{\"codigoRetorno\":197,\"codigoSequencialRetorno\":6,\"textoRetorno\":\"TRANSACAO NAO DISPONIVEL\"}",
        "debug_100_score-pix":{
           "hostname":"sgn-100-score-pix-mensageria-6d9c9f8b5d-s5mxs",
           "entrada":"17/04/2024 10:04:01:703",
           "idServico":"100",
           "nomeConfiguracao":"score-pix",
           "destino":"BB.BSB.PB.SCORE.VSAM",
           "idServicoPai":"11541",
           "sgn-mensageria-entrada":{
              "hostname":"sgn-100-score-pix-mensageria-6d9c9f8b5d-s5mxs",
              "inicio":"17/04/2024 10:04:01:702",
              "fim":"17/04/2024 10:04:01:704"
           },
           "score":{
              "hostname":"sgn-100-score-pix-score-859c9c6c4c-tjswq",
              "inicio":"17/04/2024 10:04:01:794",
              "fim":"17/04/2024 10:04:01:859"
           },
           "sgn-mensageria-saida":{
              "hostname":"sgn-100-score-pix-mensageria-saida-5dd75dddd5-rzdwb",
              "inicio":"17/04/2024 10:04:01:881",
              "fim":"17/04/2024 10:04:01:881"
           },
           "saida":"17/04/2024 10:04:01:882"
        }
     });
    const headers = { 'Content-Type': 'text/plain','Accept':'*/*' };
    const res = http.post('http://camel-quarkus-kafka-api-producer-camel.apps.cluster-2v2kt.2v2kt.sandbox322.opentlc.com/produce', payload, { headers });

    check(res, {
        'Post status is 200': (r) => res.status === 200
    });
}