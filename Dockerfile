FROM amazoncorretto:21

ARG VERSION_
ARG BUILDTIME_

ENV VERSION=$VERSION_
ENV BUILDTIME=$BUILDTIME_
ENV ADOTAPET_DIR="/srv/adotapet"
ENV PROMETHEUS_DIR="${ADOTAPET_DIR}/prometheus"



# ####### TO REMOVE - START #######

# Para corrigir um problema de acesso às URLs externas.
# É necessário importar para imagem o certificado CA autoassinado.
COPY docker/ngfw.cer /etc/pki/ca-trust/source/anchors/ngfw.cer
RUN update-ca-trust enable
RUN update-ca-trust extract

# ####### TO REMOVE - END #######



RUN yum check-update || { rc=$?; [ "$rc" -eq 100 ] && yum -y update --security; } > /build_v${VERSION}.log; \
    useradd -ms /bin/bash -d ${ADOTAPET_DIR} adotapet >> /build_v${VERSION}.log; \
    yum autoremove >> /build_v${VERSION}.log; \
    yum clean all >> /build_v${VERSION}.log; \
    rm -rf /var/cache/yum >> /build_v${VERSION}.log;

COPY --chown=adotapet:adotapet --chmod=750 docker/jmx_prometheus_javaagent.jar ${PROMETHEUS_DIR}/jmx_prometheus_javaagent.jar
COPY --chown=adotapet:adotapet --chmod=640 docker/prometheus-config.yaml ${PROMETHEUS_DIR}/config.yaml
COPY --chown=adotapet:adotapet --chmod=750 target/adotapet-backend.jar ${ADOTAPET_DIR}/adotapet-backend.jar
COPY --chown=adotapet:adotapet --chmod=750 docker/entrypoint.sh ${ADOTAPET_DIR}/entrypoint.sh

EXPOSE 8080/tcp
EXPOSE 9404/tcp

USER adotapet
WORKDIR ${ADOTAPET_DIR}
ENTRYPOINT ["/bin/bash", "-c", "${ADOTAPET_DIR}/entrypoint.sh"]
