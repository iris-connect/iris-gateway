/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package de.healthIMIS.iris.client.core;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;

/**
 * @author Jens Kutzsche
 */
@Getter
@Embeddable
public class Metadata {

	@CreatedDate
	LocalDateTime created;
	@LastModifiedDate
	LocalDateTime lastModified;
}