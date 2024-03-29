#!/usr/bin/env bash
set -euo pipefail
# This script generates test & development certificates. Not for production use!

# You can add an entry to this list to generate a certificate for a given
# operator. The name of the operator will be added as the common name as well
# as the subject alternative name (SAN), which is required for some newer
# TLS libraries.
declare -a certs=("ls-1" "sd-1" "public-proxy-1")

O="IRIS"
ST="Berlin"
L="Berlin"
C="DE"
OU="IT"
CN="Testing-Development"
# using less than 1024 here will result in a TLS handshake failure in Go
# using less than 2048 will cause e.g. 'curl' to complain that the ciper is too weak
LEN="2048"
mkdir -p certs
cd certs
if ! [ -f "root.key" ]; then
  openssl genrsa -out root.key ${LEN}
fi
if ! [ -f "root.crt" ]; then
  openssl req -x509 -new -nodes -key root.key -sha256 -days 1024 -out root.crt -subj "/C=${C}/ST=${ST}/L=${L}/O=${O}/OU=${OU}/CN=${CN}"
fi
for cert in "${certs[@]}"; do
  openssl genrsa -out "${cert}.key" ${LEN}
  openssl req -new -sha256 -key "${cert}.key" -subj "/C=${C}/ST=${ST}/L=${L}/O=${O}/OU=${OU}/CN=${cert}" -addext "subjectAltName = DNS:${cert},DNS:*.${cert}.local" -out "${cert}.csr"
  openssl x509 -req -in "${cert}.csr" -CA root.crt -CAkey root.key -CAcreateserial -out "${cert}.crt" -extensions SAN -extfile <(printf "[SAN]\nsubjectAltName = DNS:${cert},DNS:*.${cert}.local") -days 500 -sha256
done
