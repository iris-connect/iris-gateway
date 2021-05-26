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

import org.springframework.data.util.Streamable;

/**
 * @author Jens Kutzsche
 */
public interface CustomLocationRepository {
  void saveLocations(List<Location> locations);

  Streamable<Location> searchLocations(String keyword);
}
