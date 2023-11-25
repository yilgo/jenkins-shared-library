import org.manintheit.config.AlertConfig
import groovy.json.JsonOutput

// geniekey actually is the vault path where correspondend geniekey resides

def call(Map parameters) {
  def cfg = new AlertConfig(parameters)

  def secrets = [
  [
    path: "${cfg.opsgenieVaultPath}/${cfg.vaultGenieKey}", engineVersion: 2, secretValues: [
      [envVar: 'GENIEKEY', vaultKey: 'geniekey'],
    ]
  ]
]
  try {
    assert cfg.description && cfg.message
}catch (AssertionError e) {
    printf('message, description and priority can not be null')
}catch (MissingPropertyException) {
    printf('message, description and priority mandatory fields please specify them.')
  }
  def opsgenie_payload = JsonOutput.toJson([message: cfg.message.take(130),
                                         description: cfg.description,
                                         // alias can be max 512 character.
                                         // given JOB_NAME ci/global/slack-test, alias: slack-test~global~ci
                                         alias: JOB_NAME.replace('/', '~#~').split('~#~').reverse().join('~').take(512),
                                         priority: cfg.priority,
                                         repsonders: [ cfg.opsgenieResponders ],
                                         source: 'JENKINS-' + TOOLCHAIN_INSTANCE.toUpperCase() + '@' + JENKINS_INSTANCE.toUpperCase().take(100),
                                       ])
  withVault([configuration: cfg.vaultConfig, vaultSecrets: secrets]) {
        def params = [
              url: cfg.opsgenieApiURL,
              contentType: 'APPLICATION_JSON',
              httpMode: 'POST',
              customHeaders: [[name: "Authorization", value: "GenieKey " + GENIEKEY]],
              requestBody: opsgenie_payload]

        (cfg.proxy) ? params['httpProxy'] = cfg.proxyAddress : params

        //print(params)

      def response = httpRequest params
  }
}
return this

