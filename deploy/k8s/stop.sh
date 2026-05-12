#!/bin/bash

set -e

echo "🛑 Stopping ecomapp environment..."

# =========================
# 1. Kill port-forward
# =========================
echo "🔌 Stopping port-forward..."

taskkill //F //IM kubectl.exe 2>/dev/null || true

# =========================
# 2. Uninstall Helm releases
# =========================
echo "🧹 Removing Helm releases..."

# EcomApp
helm uninstall ecomapp -n ecomapp || true
helm uninstall infra -n ecomapp || true
helm uninstall postgres -n ecomapp || true
helm uninstall redis -n ecomapp || true
helm uninstall mongodb -n ecomapp || true

# Monitoring
helm uninstall monitoring -n monitoring || true
helm uninstall loki -n monitoring || true
helm uninstall promtail -n monitoring || true
helm uninstall zipkin -n monitoring || true

# =========================
# 3. Delete namespaces (optional)
# =========================
read -p "❓ Delete namespaces (ecomapp, monitoring)? (y/n): " confirm

if [ "$confirm" = "y" ]; then
  kubectl delete namespace ecomapp || true
  kubectl delete namespace monitoring || true
fi

echo "✅ Environment stopped"