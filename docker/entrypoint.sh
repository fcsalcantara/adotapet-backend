#!/bin/bash

### Variáveis customizaveis via envs ###
XMS="${XMS:-2048m}"
XMX="${XMX:-2048m}"
MAX_METASPACE_SIZE="${MAX_METASPACE_SIZE:-512m}"
TZ="${TZ:-America/Sao_Paulo}"
JAVA_EXTRA_OPTS="${JAVA_EXTRA_OPTS}"
ADOTAPET_SRV_PORT="${ADOTAPET_SRV_PORT:-8080}"
ADOTAPET_SRV_SSL_ENABLE="${ADOTAPET_SRV_SSL_ENABLE:-false}"
########################################

### Variáveis não setadas diretamente ####
JAVAOPTS=""
########################################

### Monta javaopts #####################
# Prometheus Exporter
JAVAOPTS+=" -javaagent:${ADOTAPET_DIR}/prometheus/jmx_prometheus_javaagent.jar=9404:${ADOTAPET_DIR}/prometheus/config.yaml"

# Memória
JAVAOPTS+=" -Xms${XMS} -Xmx${XMX}"
JAVAOPTS+=" -XX:MaxMetaspaceSize=${MAX_METASPACE_SIZE}"

# BUG LOG4J
JAVAOPTS+=" -Dlog4j2.formatMsgNoLookups=true"

# Timezone
JAVAOPTS+=" -Duser.timezone=${TZ}"

# Opções Extras
JAVAOPTS+=" ${JAVA_EXTRA_OPTS}"
########################################

### Inicia Aplicação ###################
java ${JAVAOPTS} -jar ${ADOTAPET_DIR}/adotapet-backend.jar
########################################
