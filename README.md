# THE UNSEEN — Aquilo que Está Aqui

Mod de terror original para Minecraft Java 1.21.1 + Fabric + Java 21.

## Conteúdo desta versão
- Eventos atmosféricos raros à noite e em áreas escuras.
- Passos que parecem vir de fora do campo de visão.
- Sons de animal em posições inesperadas.
- Três entidades com comportamentos próprios em desenvolvimento incremental.
- Compilação automatizada via GitHub Actions.

## Compilar
Abra o repositório no GitHub e entre em **Actions**. O workflow **Build mod** compila o projeto e publica o JAR como artifact quando for executado com sucesso.

Localmente, com JDK 21 e Gradle:
```sh
gradle build
```
O arquivo JAR fica em `build/libs/`.

## Instalar
Copie o JAR de `build/libs/` ou do artifact do GitHub Actions para a pasta `mods` do perfil Fabric 1.21.1. Instale também Fabric API compatível.

## Estado do projeto
O projeto deve ser considerado pronto para jogar somente depois que o workflow de build terminar com sucesso e o mod for testado no jogo. Modelos/texturas personalizados e o balanceamento de spawn podem exigir iterações adicionais.
