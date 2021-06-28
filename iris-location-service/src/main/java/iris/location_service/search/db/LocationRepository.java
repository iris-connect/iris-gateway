package iris.location_service.search.db;

import iris.location_service.search.db.model.Location;
import iris.location_service.search.db.model.LocationIdentifier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Streamable;

interface LocationRepository extends JpaRepository<Location, LocationIdentifier> {

	Page<Location> findByNameContainingIgnoreCase(String query, Pageable pageable);

	@Query("SELECT l FROM Location l WHERE fts(:query) = true")
	Page<Location> fulltextSearch(String query, Pageable pageable);

	@Modifying
	@Query(
			value = "UPDATE location SET tokens = to_tsvector('german',  CONCAT_WS(' ', name, contact_Official_Name, contact_Representative, contact_Address_Street, contact_Address_City, contact_Address_Zip, contact_Owner_Email, contact_Email, contact_Phone))",
			nativeQuery = true)
	void updateTokens();

	Streamable<Location> findByIdProviderId(String providerId);
}
