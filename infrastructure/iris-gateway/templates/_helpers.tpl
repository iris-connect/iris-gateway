{{/*
Expand the name of the chart.
*/}}
{{- define "iris-gateway.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "iris-gateway.fullname" -}}
{{- $name := .Chart.Name }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}

{{- define "iris-gateway.locations" -}}
{{- include "iris-gateway.fullname" . }}-locations
{{- end }}

{{- define "iris-gateway.locations-eps" -}}
{{- include "iris-gateway.fullname" . }}-locations-eps
{{- end }}

{{- define "iris-gateway.locations-postgres" -}}
{{ include "iris-gateway.fullname" . }}-locations-postgres
{{- end }}

{{- define "iris-gateway.locations-postgres-backup" -}}
{{ include "iris-gateway.fullname" . }}-locations-postgres-backup
{{- end }}

{{- define "iris-gateway.service-directory" -}}
{{ include "iris-gateway.fullname" . }}-service-directory
{{- end }}

{{- define "iris-gateway.service-directory-backup" -}}
{{ include "iris-gateway.fullname" . }}-service-directory-backup
{{- end }}

{{- define "iris-gateway.public-proxy" -}}
{{ include "iris-gateway.fullname" . }}-public-proxy
{{- end }}

{{- define "iris-gateway.public-proxy-eps" -}}
{{ include "iris-gateway.fullname" . }}-public-proxy-eps
{{- end }}

{{- define "iris-gateway.locations-labels" -}}
app.kubernetes.io/name: {{ include "iris-gateway.locations" . }}
{{- end }}

{{- define "iris-gateway.locations-eps-labels" -}}
app.kubernetes.io/name: {{ include "iris-gateway.locations-eps" . }}
{{- end }}

{{- define "iris-gateway.locations-postgres-labels" -}}
app.kubernetes.io/name: {{ include "iris-gateway.locations-postgres" . }}
{{- end }}

{{- define "iris-gateway.locations-postgres-backup-labels" -}}
app.kubernetes.io/name: {{ include "iris-gateway.locations-postgres-backup" . }}
{{- end }}

{{- define "iris-gateway.service-directory-labels" -}}
app.kubernetes.io/name: {{ include "iris-gateway.service-directory" . }}
{{- end }}

{{- define "iris-gateway.service-directory-backup-labels" -}}
app.kubernetes.io/name: {{ include "iris-gateway.service-directory-backup" . }}
{{- end }}

{{- define "iris-gateway.public-proxy-labels" -}}
app.kubernetes.io/name: {{ include "iris-gateway.public-proxy" . }}
{{- end }}

{{- define "iris-gateway.public-proxy-eps-labels" -}}
app.kubernetes.io/name: {{ include "iris-gateway.public-proxy-eps" . }}
{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "iris-gateway.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}


{{/*
Pullpolicy to enable local testing
*/}}
{{- define "iris-gateway.pullPolicy" -}}
{{- if eq "local" .Values.environment }}
	{{- "IfNotPresent" }}
{{- else }}
	{{- "Always" }}
{{- end }}
{{- end }}
