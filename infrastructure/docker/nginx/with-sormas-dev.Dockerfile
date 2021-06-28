FROM nginx
COPY config-dev/nginx.conf /etc/nginx/
COPY config-dev/sites-enabled/ /etc/nginx/sites-enabled/
COPY config-dev/sites-enabled-sormas/ /etc/nginx/sites-enabled/
