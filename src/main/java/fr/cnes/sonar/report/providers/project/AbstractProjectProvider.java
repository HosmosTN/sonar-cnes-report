/*
 * This file is part of cnesreport.
 *
 * cnesreport is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * cnesreport is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with cnesreport.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.cnes.sonar.report.providers.project;

import java.util.HashMap;
import java.util.Map;

import org.sonarqube.ws.client.WsClient;

import com.google.gson.JsonObject;

import fr.cnes.sonar.report.exceptions.BadSonarQubeRequestException;
import fr.cnes.sonar.report.exceptions.SonarQubeException;
import fr.cnes.sonar.report.model.Language;
import fr.cnes.sonar.report.model.ProfileMetaData;
import fr.cnes.sonar.report.model.Project;
import fr.cnes.sonar.report.providers.AbstractDataProvider;
import fr.cnes.sonar.report.providers.language.LanguageProvider;
import fr.cnes.sonar.report.utils.StringManager;

/**
 * Contains common code for project providers
 */
public abstract class AbstractProjectProvider extends AbstractDataProvider {
    /**
     * Name of the request for getting quality profiles
     */
    protected static final String GET_QUALITY_PROFILES_REQUEST = "GET_QUALITY_PROFILES_REQUEST";
    /**
     * Field used in API responses to wrap component data
     */
    private static final String COMPONENT = "component";
    /**
     * Field used in API responses for project quality profiles
     */
    private static final String QUALITY_PROFILES = "qualityProfiles";
    /**
     * Field used in API responses for profiles search
     */
    private static final String PROFILES = "profiles";

    /**
     * The language provider
     */
    protected LanguageProvider languageProvider;

    /**
     * Complete constructor.
     * @param pServer SonarQube server.
     * @param pToken String representing the user token.
     * @param pProject The id of the project to report.
     * @param pBranch The branch of the project to report.
     * @param pLanguageProvider The language provider.
     */
    protected AbstractProjectProvider(final String pServer, final String pToken, final String pProject,
            final String pBranch, final LanguageProvider pLanguageProvider) {
        super(pServer, pToken, pProject, pBranch);
        this.languageProvider = pLanguageProvider;
    }

    /**
     * Complete constructor.
     * @param wsClient The web client.
     * @param project The id of the project to report.
     * @param branch The branch of the project to report.
     * @param languageProvider The language provider.
     */
    protected AbstractProjectProvider(final WsClient wsClient, final String project, final String branch, final LanguageProvider languageProvider) {
        super(wsClient, project, branch);
        this.languageProvider = languageProvider;
    }

    /**
     * Generic getter for the project corresponding to the given key.
     * @param projectKey the key of the project.
     * @param branch the branch of the project.
     * @return A simple project.
     * @throws BadSonarQubeRequestException when the server does not understand the request.
     * @throws SonarQubeException When SonarQube server is not callable.
     */
    protected Project getProjectAbstract(final String projectKey, final String branch)
            throws BadSonarQubeRequestException, SonarQubeException {
        final JsonObject rawResponse = getProjectAsJsonObject(projectKey, branch);
        final JsonObject jo = normalizeProjectJsonObject(rawResponse);

        // put json in a Project class
        final Project project = (getGson().fromJson(jo, Project.class));
        ProfileMetaData[] metaData = getProjectQualityProfiles(jo, projectKey);

        // set language's name for profiles and add each language to the project languages list
        String languageName;
        Map<String, Language> languages = new HashMap<>();
        for(ProfileMetaData it : metaData){
            String languageKey = it.getLanguage();

            languageName = languageKey;
            if (languageProvider != null && languageProvider.getLanguages() != null) {
                final String resolvedLanguage = languageProvider.getLanguages().getLanguage(languageKey);
                if (resolvedLanguage != null) {
                    languageName = resolvedLanguage;
                }
            }
            it.setLanguageName(languageName);

            Language language = new Language();
            language.setKey(languageKey);
            language.setName(languageName);
            languages.put(languageKey, language);
        }
        
        project.setQualityProfiles(metaData);
        project.setLanguages(languages);

        // check description nullity
        if(null == project.getDescription()) {
            project.setDescription(StringManager.EMPTY);
        }
        // check version nullity
        if(null == project.getVersion()) {
            project.setVersion(StringManager.EMPTY);
        }
        // preserve the requested branch when API does not return one
        if (project.getBranch() == null || project.getBranch().isEmpty()) {
            project.setBranch(branch);
        }

        return project;
    }

    /**
     * Generic method to check if a project exists on a SonarQube instance.
     * @param projectKey the key of the project.
     * @param branch the branch of the project.
     * @return True if the project exists.
     * @throws BadSonarQubeRequestException when the server does not understand the request.
     * @throws SonarQubeException When SonarQube server is not callable.
     */
    protected boolean hasProjectAbstract(final String projectKey, final String branch)
            throws BadSonarQubeRequestException, SonarQubeException {
        final JsonObject rawResponse = getProjectAsJsonObject(projectKey, branch);
        final JsonObject jsonObject = normalizeProjectJsonObject(rawResponse);

        if (!jsonObject.has("key") || jsonObject.get("key").isJsonNull()) {
            return false;
        }

        // Retrieve project key if the project exists or null.
        final String project = jsonObject.get("key").getAsString();

        return project != null && project.equals(projectKey);
    }

    /**
     * Normalize project API response shape.
     * Some public APIs return a top-level "component" object while legacy APIs returned project fields at root.
     * @param response Raw response from SonarQube.
     * @return The JsonObject containing project fields.
     */
    private JsonObject normalizeProjectJsonObject(final JsonObject response) {
        if (response.has(COMPONENT) && response.get(COMPONENT).isJsonObject()) {
            return response.getAsJsonObject(COMPONENT);
        }
        return response;
    }

    /**
     * Retrieve quality profiles attached to a project, from project payload or dedicated quality profile API.
     * @param projectJson Normalized project JSON object.
     * @param projectKey SonarQube project key.
     * @return Array of quality profile metadata.
     * @throws BadSonarQubeRequestException when the server does not understand the request.
     * @throws SonarQubeException When SonarQube server is not callable.
     */
    private ProfileMetaData[] getProjectQualityProfiles(final JsonObject projectJson, final String projectKey)
            throws BadSonarQubeRequestException, SonarQubeException {
        if (projectJson.has(QUALITY_PROFILES) && projectJson.get(QUALITY_PROFILES).isJsonArray()) {
            final ProfileMetaData[] embedded = getGson().fromJson(projectJson.get(QUALITY_PROFILES), ProfileMetaData[].class);
            return embedded != null ? embedded : new ProfileMetaData[0];
        }

        final JsonObject qualityProfilesResponse = getProjectQualityProfilesAsJsonObject(projectKey);
        if (qualityProfilesResponse != null
                && qualityProfilesResponse.has(PROFILES)
                && qualityProfilesResponse.get(PROFILES).isJsonArray()) {
            final ProfileMetaData[] fetched = getGson().fromJson(qualityProfilesResponse.get(PROFILES), ProfileMetaData[].class);
            return fetched != null ? fetched : new ProfileMetaData[0];
        }

        return new ProfileMetaData[0];
    }

    /**
     * Get a JsonObject from the response of a get component request.
     * @return The response as a JsonObject.
     * @throws BadSonarQubeRequestException A request is not recognized by the server.
     * @throws SonarQubeException When SonarQube server is not callable.
     */
    protected abstract JsonObject getProjectAsJsonObject(final String projectKey, final String branch)
            throws BadSonarQubeRequestException, SonarQubeException;

    /**
     * Get quality profiles linked to the project from SonarQube API.
     * @param projectKey the key of the project.
     * @return The response as a JsonObject.
     * @throws BadSonarQubeRequestException A request is not recognized by the server.
     * @throws SonarQubeException When SonarQube server is not callable.
     */
    protected abstract JsonObject getProjectQualityProfilesAsJsonObject(final String projectKey)
            throws BadSonarQubeRequestException, SonarQubeException;
}
