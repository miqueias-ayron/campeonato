# Campeonato de Futebol – API REST

API REST para gerenciar um campeonato de futebol: cadastro de jogadores e times,
registro de partidas, placar, cartões e tabela de classificação.

Construída com **Spring Boot** em arquitetura de camadas (`Controller` → `Service` → `Model`).
O armazenamento é **em memória** (`HashMap` nos services); ao reiniciar a aplicação os dados são perdidos.

## Requisitos

- Java 21
- Maven (o projeto já inclui o wrapper `./mvnw` / `mvnw.cmd`)

## Como rodar

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

Documentação interativa (Swagger UI): `http://localhost:8080/swagger-ui.html`

## Camadas

```
src/main/java/com/campeonato/campeonato
├── jogador/      Jogador  + controller + service
├── time/         Time     + controller + service
├── partida/      Partida  + controller + service
├── cartao/       Cartao (model)
└── campeonato/   Campeonato + controller + service
```

## Endpoints

### Jogadores – `/jogadores`

| Método | Rota               | Descrição                     | Status |
|--------|--------------------|-------------------------------|--------|
| POST   | `/jogadores`       | Cadastra um jogador           | 201    |
| GET    | `/jogadores`       | Lista todos os jogadores      | 200    |
| GET    | `/jogadores/{id}`  | Busca um jogador por ID       | 200 / 404 |

Body do POST:
```json
{ "nome": "Pelé", "numero": 10, "posicao": "ATACANTE" }
```

### Times – `/times`

| Método | Rota                                        | Descrição                                       | Status |
|--------|---------------------------------------------|------------------------------------------------|--------|
| POST   | `/times`                                    | Cadastra um time                               | 201    |
| GET    | `/times`                                    | Lista todos os times                           | 200    |
| GET    | `/times/{id}`                               | Busca um time por ID + lista de seus jogadores | 200 / 404 |
| POST   | `/times/{idTime}/jogadores/{idJogador}`     | Adiciona um jogador existente ao time          | 200 / 404 |

Body do POST:
```json
{ "nome": "Santos", "cidade": "Santos" }
```

### Partidas – `/partidas`

| Método | Rota                          | Descrição                                              | Status |
|--------|-------------------------------|-------------------------------------------------------|--------|
| POST   | `/partidas`                   | Registra uma partida (data, mandante, visitante)      | 201 / 400 / 404 |
| GET    | `/partidas`                   | Lista todas as partidas                               | 200    |
| GET    | `/partidas/{id}`              | Busca uma partida por ID                              | 200 / 404 |
| PATCH  | `/partidas/{id}/placar`       | Atualiza os gols (`golMandante` / `golVisitante`)     | 200 / 400 / 404 |
| POST   | `/partidas/{id}/cartoes`      | Registra um cartão para um jogador                    | 201 / 400 / 404 |

Body do POST `/partidas` (mandante e visitante **não** podem ser o mesmo time → `400`):
```json
{ "data": "2024-05-01", "idMandante": "<id-time>", "idVisitante": "<id-time>" }
```

Body do PATCH `/partidas/{id}/placar`:
```json
{ "golMandante": 2, "golVisitante": 1 }
```

Body do POST `/partidas/{id}/cartoes` (`tipo` deve ser `AMARELO` ou `VERMELHO` → senão `400`):
```json
{ "idJogador": "<id-jogador>", "tipo": "AMARELO", "minuto": 37 }
```

> A pontuação da partida é aplicada aos times na **primeira** atualização de placar
> (Vitória = 3, Empate = 1, Derrota = 0). Atualizações seguintes do placar não somam
> pontos novamente, para evitar contagem em duplicidade.

### Campeonatos – `/campeonatos`

| Método | Rota                                 | Descrição                                             | Status |
|--------|--------------------------------------|------------------------------------------------------|--------|
| POST   | `/campeonatos`                       | Cria um campeonato (associa times por ID)            | 201 / 404 |
| GET    | `/campeonatos/{id}/classificacao`    | Tabela de classificação ordenada por pontos (desc.)  | 200 / 404 |

Body do POST:
```json
{ "nome": "Brasileirão", "ano": 2024, "timeIds": ["<id-time-1>", "<id-time-2>"] }
```

Resposta da classificação (`nome do time` → `pontos`):
```json
{ "Santos": 3, "Palmeiras": 0 }
```

> Use os **mesmos IDs de time** ao criar o campeonato e ao registrar as partidas:
> os pontos são acumulados no time e a classificação do campeonato lê esses pontos.

## Testando via cURL (fluxo completo)

```bash
BASE=http://localhost:8080

# 1. Jogador
JID=$(curl -s -XPOST $BASE/jogadores -H 'Content-Type: application/json' \
  -d '{"nome":"Pelé","numero":10,"posicao":"ATACANTE"}' | python -c 'import sys,json;print(json.load(sys.stdin)["id"])')

# 2. Times
T1=$(curl -s -XPOST $BASE/times -H 'Content-Type: application/json' \
  -d '{"nome":"Santos","cidade":"Santos"}' | python -c 'import sys,json;print(json.load(sys.stdin)["id"])')
T2=$(curl -s -XPOST $BASE/times -H 'Content-Type: application/json' \
  -d '{"nome":"Palmeiras","cidade":"São Paulo"}' | python -c 'import sys,json;print(json.load(sys.stdin)["id"])')

# 3. Jogador no time
curl -s -XPOST $BASE/times/$T1/jogadores/$JID

# 4. Campeonato
CID=$(curl -s -XPOST $BASE/campeonatos -H 'Content-Type: application/json' \
  -d "{\"nome\":\"Brasileirão\",\"ano\":2024,\"timeIds\":[\"$T1\",\"$T2\"]}" | python -c 'import sys,json;print(json.load(sys.stdin)["id"])')

# 5. Partida
PID=$(curl -s -XPOST $BASE/partidas -H 'Content-Type: application/json' \
  -d "{\"data\":\"2024-05-01\",\"idMandante\":\"$T1\",\"idVisitante\":\"$T2\"}" | python -c 'import sys,json;print(json.load(sys.stdin)["id"])')

# 6. Placar 2 x 1 (Santos vence → +3)
curl -s -XPATCH $BASE/partidas/$PID/placar -H 'Content-Type: application/json' \
  -d '{"golMandante":2,"golVisitante":1}'

# 7. Cartão
curl -s -XPOST $BASE/partidas/$PID/cartoes -H 'Content-Type: application/json' \
  -d "{\"idJogador\":\"$JID\",\"tipo\":\"AMARELO\",\"minuto\":37}"

# 8. Classificação
curl -s $BASE/campeonatos/$CID/classificacao
```

## Testando via IntelliJ HTTP Client

O arquivo [`requests.http`](requests.http) na raiz do projeto contém todas as chamadas
encadeadas com variáveis. Abra no IntelliJ e execute de cima para baixo.
