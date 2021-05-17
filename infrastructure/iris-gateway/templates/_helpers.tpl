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

{{- define "iris-gateway.locations-labels" -}}
app.kubernetes.io/name: {{ include "iris-gateway.locations" . }}
{{- end }}

{{- define "iris-gateway.locations-eps-labels" -}}
app.kubernetes.io/name: {{ include "iris-gateway.locations-eps" . }}
{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "iris-gateway.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}
