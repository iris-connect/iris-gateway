package iris.backend_service.alerts;

import iris.backend_service.alerts.Alert.AlertIdentifier;

import org.springframework.data.jpa.repository.JpaRepository;

interface AlertRepository extends JpaRepository<Alert, AlertIdentifier> {}
