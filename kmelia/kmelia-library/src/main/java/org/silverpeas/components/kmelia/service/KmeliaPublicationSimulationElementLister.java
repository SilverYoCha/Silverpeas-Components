/*
 * Copyright (C) 2000 - 2018 Silverpeas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * As a special exception to the terms and conditions of version 3.0 of
 * the GPL, you may redistribute this Program in connection with Free/Libre
 * Open Source Software ("FLOSS") applications as described in Silverpeas's
 * FLOSS exception. You should have recieved a copy of the text describing
 * the FLOSS exception, and it is also available here:
 * "http://www.silverpeas.org/docs/core/legal/floss_exception.html"
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.silverpeas.components.kmelia.service;

import org.silverpeas.core.io.media.image.thumbnail.control.ThumbnailSimulationElement;
import org.silverpeas.core.WAPrimaryKey;
import org.silverpeas.core.node.model.NodePK;
import org.silverpeas.core.contribution.publication.model.PublicationDetail;
import org.silverpeas.core.contribution.publication.model.PublicationPK;
import org.silverpeas.core.contribution.attachment.process.AttachmentSimulationElementLister;
import org.silverpeas.core.process.annotation.SimulationElementLister;

import java.util.Collection;

/**
 * User: Yohann Chastagnier
 * Date: 24/10/13
 */
public class KmeliaPublicationSimulationElementLister
    extends AbstractKmeliaSimulationElementLister {

  public KmeliaPublicationSimulationElementLister() {
    super();
  }

  public KmeliaPublicationSimulationElementLister(
      final SimulationElementLister parentElementLister) {
    super(parentElementLister);
  }

  @Override
  public void listElements(final WAPrimaryKey sourcePK, final String language) {
    if (sourcePK instanceof NodePK) {
      listPublicationDocumentsFromNode((NodePK) sourcePK, language);
    } else {
      listPublicationDocumentsFromPublication(getPublicationService()
          .getDetail(new PublicationPK(sourcePK.getId(), sourcePK.getInstanceId())), language);
    }
  }

  /**
   * Gets the list of attachments associated to a node
   * @param nodePK
   * @param language
   * @return
   */
  private void listPublicationDocumentsFromNode(NodePK nodePK, String language) {

    // Retrieving all publication of a node
    Collection<PublicationDetail> publications = getPublicationService().getDetailsByFatherPK(nodePK);
    for (PublicationDetail publication : publications) {

      // Using here the default implementation of document lister
      listPublicationDocumentsFromPublication(publication, language);
    }
  }

  /**
   * Gets the list of attachments associated to a publication
   * @param publication
   * @param language
   * @return
   */
  private void listPublicationDocumentsFromPublication(PublicationDetail publication,
      String language) {

    // Thumbnail
    addElement(new ThumbnailSimulationElement(publication.getThumbnail()));

    // Using here the default implementation of document lister
    new AttachmentSimulationElementLister(this).listElements(publication.getPK(), language);
  }
}
