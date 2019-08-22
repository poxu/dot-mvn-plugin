package poxu.plugin;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.annotations.SystemIndependent;
import org.jetbrains.idea.maven.execution.MavenRunner;
import org.jetbrains.idea.maven.project.MavenGeneralSettings;
import org.jetbrains.idea.maven.project.MavenProjectsManager;
import org.jetbrains.idea.maven.server.MavenServerManager;

import java.io.File;

public class DotMvnProjectComponent implements ProjectComponent {
    private final Project project;
    private Logger log = Logger.getInstance(this.getClass());

    public DotMvnProjectComponent(Project project) {
        this.project = project;
    }

    @Override
    public void projectOpened() {
        VirtualFileManager.getInstance().addVirtualFileListener(new DotMvnChangeListener(project));
        @SystemIndependent
        final String basePath = project.getBasePath();
        if (basePath == null) {
            log.info("couldn't find project baseDir");
        }
        updateMavenSettings(basePath);
    }

    private void updateMavenSettings(@SystemIndependent String basePath) {
        final File dotMvn = new File(basePath + "/.mvn");
        if (!dotMvn.exists()) {
            log.info("couldn't find .mvn directory");
            return;
        }
        final File jvmConfig = new File(basePath + "/.mvn/jvm.config");
        if (!jvmConfig.exists()) {
            log.info("couldn't find /.mvn/jvm.config");
            return;
        }

        MavenGeneralSettings generalSettings = MavenProjectsManager.getInstance(project).getGeneralSettings();
        if (generalSettings == null) {
            log.info("Maven plugin not found");
            return;
        }

        MavenOpts mavenOpts = new MavenOpts(jvmConfig);
        mavenOpts.embed(MavenRunner.getInstance(project).getSettings());
        mavenOpts.embed(MavenServerManager.getInstance());
    }
}
