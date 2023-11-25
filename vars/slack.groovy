import org.manintheit.config.AlertConfig
import groovy.json.JsonOutput

  def call(Map parameters){

    def cfg = new AlertConfig(parameters)

    def secrets = [
      [
        path: "${cfg.slackVaultPath}/${cfg.channel}", secretValues: [
          [envVar: "SLACK_WEBHOOK_URL", vaultKey: "webhook_url"],
        ]
      ]
    ]

    def slack_payload = JsonOutput.toJson([text : cfg.message,
                                      channel   : "#".join(cfg.channel),
                                      username  : "jenkins-alert@${JENKINS_INSTANCE}",
                                      icon_emoji: cfg.slackIconEmoji])

    withVault([configuration: cfg.vaultConfig, vaultSecrets: secrets]){
    def params = [
                  url: SLACK_WEBHOOK_URL,
                  contentType: "APPLICATION_JSON",
                  httpMode: "POST",
                  requestBody: slack_payload]

    (cfg.proxy) ? params["httpProxy"] = cfg.proxyAddress : params

    def response = httpRequest params
    }

 }
return this
