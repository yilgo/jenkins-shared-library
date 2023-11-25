package org.manintheit.config
class Config{
  public static final String jenkinsCredsID = "jenkins-approle"
  public static final proxyAddress = "webproxy.lab.io:3128"
  public Boolean proxy = false

  def vaultConfig = [
                      vaultCredentialId: jenkinsCredsID,
                      engineVersion: 2
                    ]


}
