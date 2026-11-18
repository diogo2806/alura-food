# Gitpod development workspace, not a production runtime.
# Base tag published by Gitpod: https://hub.docker.com/r/gitpod/workspace-full/tags
FROM gitpod/workspace-full:2026-02-12-23-06-03

USER gitpod

RUN sudo apt-get update \
    && sudo apt-get install -y mysql-server curl ca-certificates \
    && sudo apt-get clean && sudo rm -rf /var/cache/apt/* && sudo rm -rf /var/lib/apt/lists/* && sudo rm -rf /tmp/*

# Amazon Corretto 17.0.21.1.2 fixes CVE-2026-114938.
# https://github.com/corretto/corretto-17/blob/17.0.21.1.2/CHANGELOG.md
# SHA256: https://github.com/corretto/corretto-17/releases/tag/17.0.21.1.2
ARG CORRETTO_VERSION=17.0.21.1.2
ARG CORRETTO_SHA256=d8a350b5988f58e6186ddc1db584af170e9d56862816d4a578e9570d85307e2c
RUN curl --fail --silent --show-error --location \
        "https://corretto.aws/downloads/resources/${CORRETTO_VERSION}/amazon-corretto-${CORRETTO_VERSION}-linux-x64.tar.gz" \
        --output /tmp/amazon-corretto.tar.gz \
    && echo "${CORRETTO_SHA256}  /tmp/amazon-corretto.tar.gz" | sha256sum --check \
    && sudo mkdir -p /opt/amazon-corretto-17 \
    && sudo tar -xzf /tmp/amazon-corretto.tar.gz -C /opt/amazon-corretto-17 --strip-components=1 \
    && rm /tmp/amazon-corretto.tar.gz

ENV JAVA_HOME=/opt/amazon-corretto-17
ENV PATH="${JAVA_HOME}/bin:${PATH}"

RUN test -x "${JAVA_HOME}/bin/java" && java -version && javac -version

EXPOSE 3306
