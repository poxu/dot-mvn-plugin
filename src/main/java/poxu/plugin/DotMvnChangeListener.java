package poxu.plugin;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileListener;
import com.intellij.openapi.vfs.VirtualFileMoveEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.idea.maven.execution.MavenRunner;
import org.jetbrains.idea.maven.server.MavenServerManager;

import java.io.File;

public class DotMvnChangeListener implements VirtualFileListener {

    private Logger log = Logger.getInstance(this.getClass());
    private final Project project;

    public DotMvnChangeListener(Project project) {
        this.project = project;
    }

    @Override
    public void contentsChanged(@NotNull VirtualFileEvent event) {
        updateMavenSettings(event);
    }

    private void updateMavenSettings(@NotNull VirtualFileEvent event) {
        if (event.getFileName().contains("jvm.config")) {
            final File jvmConfig = new File(event.getFile().getCanonicalPath());
            MavenOpts mavenOpts = new MavenOpts(jvmConfig);
            mavenOpts.embed(MavenRunner.getInstance(project).getSettings());
            mavenOpts.embed(MavenServerManager.getInstance());
        }
    }

    @Override
    public void fileCreated(@NotNull VirtualFileEvent event) {
        updateMavenSettings(event);
    }

    @Override
    public void fileDeleted(@NotNull VirtualFileEvent event) {
        MavenOpts mavenOpts = new MavenOpts("");
        mavenOpts.embed(MavenRunner.getInstance(project).getSettings());
        mavenOpts.embed(MavenServerManager.getInstance());
    }

    @Override
    public void fileMoved(@NotNull VirtualFileMoveEvent event) {
        log.warn("old parent = " + event.getOldParent().getCanonicalPath());
        log.warn("new parent = " + event.getNewParent().getCanonicalPath());
        log.warn("new parent = " + event.getFileName());
    }
}
