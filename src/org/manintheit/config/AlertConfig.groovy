package org.manintheit.config

class AlertConfig extends Config {
  public static String slackVaultPath = "secret/global/slack"
  public static String slackIconEmoji = ":jenkins:"
  public String message = ""
  //default slack channel
  public String channel = "ocp-alerts"

  // if opsgenie location is EU, otherwise https://api.opsgenie.com
  public static final String opsgenieApiURL = "https://api.eu.opsgenie.com/v2/alerts"
  public static final String opsgenieVaultPath = "secret/global/opsgenie"
  // name of secret in vault specified by opsgenieVaultPath
  public static final String vaultGenieKey = "jenkins"

  public static final Map opsgenieResponders = [
                                      type: "team",
                                      name: "your_lovely_team_in_opsgenie"
  ]
  // default priority is P3
  public String priority = "P3"

  //String message = ""
  public String description = ""
  public String alias = ""
  //String proxy = ""
}
