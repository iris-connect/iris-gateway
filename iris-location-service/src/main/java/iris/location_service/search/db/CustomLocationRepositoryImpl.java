/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package iris.location_service.search.db;

import iris.location_service.search.db.model.Location;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Streamable;

/**
 * @author Jens Kutzsche
 */
public class CustomLocationRepositoryImpl implements CustomLocationRepository {

  @Value("${spring.datasource.driverClassName:}")
  private String datasourceDriver;

  @PersistenceContext
  private EntityManager em;

  @Override
  @Transactional
  public void saveLocations(List<Location> locations) {

	locations.forEach(em::persist);

	if (isPostgresActive()) {
	  em.createNamedQuery("Location.update.tokens").executeUpdate();
	}
  }

  @Override
  public Streamable<Location> searchLocations(String keyword) {

	TypedQuery<Location> query;

	if (isPostgresActive()) {

	  query = em.createNamedQuery("Location.fulltext", Location.class);
	  query.setParameter(1, keyword);

	} else {

	  query = em.createNamedQuery("Location.findByName", Location.class);
	  query.setParameter("keyword", keyword);
	}

	return Streamable.of(query.getResultList());
  }

  private boolean isPostgresActive() {
	return datasourceDriver.equals("org.postgresql.Driver");
  }
}
