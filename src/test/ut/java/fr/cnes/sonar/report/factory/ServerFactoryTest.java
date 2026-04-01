package fr.cnes.sonar.report.factory;

import fr.cnes.sonar.report.CommonTest;
import fr.cnes.sonar.report.exceptions.BadSonarQubeRequestException;
import fr.cnes.sonar.report.exceptions.SonarQubeException;
import fr.cnes.sonar.report.model.SonarQubeServer;
import fr.cnes.sonar.report.providers.sonarqubeinfo.SonarQubeInfoProvider;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ServerFactoryTest extends CommonTest {

    @Test
    public void createStandaloneTest() throws BadSonarQubeRequestException {
        try {
            ServerFactory serverFactory = new ServerFactory("http://biiiiiim", standaloneProviderFactory);
            serverFactory.create();
        } catch (SonarQubeException e) {
            Assert.assertEquals("Impossible to reach SonarQube instance.", e.getMessage());
        }
    }

    @Test
    public void createSupportCheckFor25VersionTest() throws BadSonarQubeRequestException, SonarQubeException {
        final SonarQubeInfoProvider infoProvider = Mockito.mock(SonarQubeInfoProvider.class);
        final ProviderFactory providerFactory = Mockito.mock(ProviderFactory.class);
        Mockito.when(providerFactory.createSonarQubeInfoProvider()).thenReturn(infoProvider);
        Mockito.when(infoProvider.getSonarQubeStatus()).thenReturn("UP");
        Mockito.when(infoProvider.getSonarQubeVersion()).thenReturn("25.1.0.102418");

        final ServerFactory serverFactory = new ServerFactory("http://localhost:9000", providerFactory);
        final SonarQubeServer server = serverFactory.create();
        Assert.assertTrue(server.isSupported());
    }

    @Test
    public void createSupportCheckFor2025VersionTest() throws BadSonarQubeRequestException, SonarQubeException {
        final SonarQubeInfoProvider infoProvider = Mockito.mock(SonarQubeInfoProvider.class);
        final ProviderFactory providerFactory = Mockito.mock(ProviderFactory.class);
        Mockito.when(providerFactory.createSonarQubeInfoProvider()).thenReturn(infoProvider);
        Mockito.when(infoProvider.getSonarQubeStatus()).thenReturn("UP");
        Mockito.when(infoProvider.getSonarQubeVersion()).thenReturn("2025.1.0.12345");

        final ServerFactory serverFactory = new ServerFactory("http://localhost:9000", providerFactory);
        final SonarQubeServer server = serverFactory.create();
        Assert.assertTrue(server.isSupported());
    }

    @Test
    public void createSupportCheckForUnsupportedVersionTest() throws BadSonarQubeRequestException, SonarQubeException {
        final SonarQubeInfoProvider infoProvider = Mockito.mock(SonarQubeInfoProvider.class);
        final ProviderFactory providerFactory = Mockito.mock(ProviderFactory.class);
        Mockito.when(providerFactory.createSonarQubeInfoProvider()).thenReturn(infoProvider);
        Mockito.when(infoProvider.getSonarQubeStatus()).thenReturn("UP");
        Mockito.when(infoProvider.getSonarQubeVersion()).thenReturn("11.0.0.1");

        final ServerFactory serverFactory = new ServerFactory("http://localhost:9000", providerFactory);
        final SonarQubeServer server = serverFactory.create();
        Assert.assertFalse(server.isSupported());
    }
}
