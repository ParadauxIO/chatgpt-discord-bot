# Sylvester: A Discord ChatGPT Experience

[![Version](https://img.shields.io/badge/version-1.0.0-%23232825)]()
![License](https://img.shields.io/badge/license-GPLv3-%23CCCC)
[![Status Badge](https://img.shields.io/badge/status-beta-236b88)]()
[![Deployed Badge](https://img.shields.io/badge/written%20in-java/jda-F6821F)]()

This project is a simple-to-use Discord Bot which allows you to have interactive
experiences with ChatGPT as if it was a member of your discord server.

# Installation

```bash
git clone https://github.com/ParadauxIO/chatgpt-discord-bot.git
```

Edit the configuration file with your [Discord Bot Token](https://www.writebots.com/discord-bot-token/), [OpenAI key](https://openai.com/blog/openai-api) 
and configure what servers you'd like ChatGPT to listen in on. 

Make sure to place `config.json` in the root of the downloaded project before building the docker image, otherwise it won't be included.
If you are running it standalone, without docker you can run the program once to generate the defautl config, otherwise copy it from below:

```json
{
  "discord_token": "YOUR_TOKEN",
  "openai_token": "YOUR_TOKEN",
  "guilds": {
    "DISCORD_SERVER_ID": "CHANNEL_ID",
    "OPTIONAL SECOND DISCORD": "CHANNEL ID"
  }
}
```

**N.B**: The bot requires the `MESSAGE_CONTNET` intent in order to function, and you will 
need to configure billing for the OpenAI/ChatGPT aspect to function as it is paid functionality. 
Cost is currently ~ $0.002 per 1k tokens, which equates to around 250 characters generated.

# Run using docker
Build the docker image as follows, I'm not providing a dockerhub version to remove any expectation of support.
```
docker build -t sylvester . 
```

Once configured you can run the bot as follows:
```
docker run sylvester
```

# Run standalone

You can either build an uberjar using 
```
./gradlew shadowJar
```

and executing it as normal using
```
java -jar DiscordChatGPT-1.0.0-all.jar
```

Or you can run it directly from gradle:
```
./gradlew run
```