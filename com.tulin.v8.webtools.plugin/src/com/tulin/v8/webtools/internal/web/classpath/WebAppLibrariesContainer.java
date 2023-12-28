/******************************************************************************
 * Copyright (c) 2005-2006 BEA Systems, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *    Konstantin Komissarchik - initial API and implementation
 ******************************************************************************/

package com.tulin.v8.webtools.internal.web.classpath;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.osgi.util.NLS;

import com.tulin.v8.webtools.htmleditor.HTMLPlugin;

/**
 * @author <a href="mailto:kosta@bea.com">Konstantin Komissarchik</a>
 * @author chenqian
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class WebAppLibrariesContainer extends FlexibleProjectContainer {
	private WebAppLibrariesStore.RestoreState restoreState;

	private static final IPath[] paths = new IPath[] { new Path("WEB-INF/lib"), //$NON-NLS-1$
			new Path("WEB-INF/classes") }; //$NON-NLS-1$

	private static final PathType[] types = new PathType[] { PathType.LIB_DIRECTORY, PathType.CLASSES_DIRECTORY };

	public static final String CONTAINER_ID = "org.eclipse.jst.j2ee.internal.web.container"; //$NON-NLS-1$

	public WebAppLibrariesContainer(final IPath path, final IJavaProject jproject) {
		super(path, jproject, getProject(path, jproject), paths, types, true);
		boolean needToVerify = false;
		if (null != restoreState) {
			synchronized (restoreState) {
				needToVerify = restoreState.needToVerify;
				restoreState.needToVerify = false;
			}
		}
		if (needToVerify) {
			Job verifyJob = new Job(Resources.verify) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					WebAppLibrariesContainer.this.refresh(false);
					return Status.OK_STATUS;
				}
			};
			verifyJob.setSystem(true);
			verifyJob.setRule(ResourcesPlugin.getWorkspace().getRoot());
			verifyJob.schedule();
		}
	}

	public String getDescription() {
		if (this.owner.getProject() != this.project) {
			return NLS.bind(Resources.labelWithProject, this.project.getName());
		}
		return Resources.label;
	}

	public void install() {
		final IJavaProject[] projects = new IJavaProject[] { this.owner };
		final IClasspathContainer[] conts = new IClasspathContainer[] { this };

		try {
			JavaCore.setClasspathContainer(path, projects, conts, null);
		} catch (JavaModelException e) {
			HTMLPlugin.logError(e);
		}
	}

	@Override
	public boolean isOutOfDate() {
//		if (IDependencyGraph.INSTANCE.isStale()) {
//			// avoid deadlock https://bugs.eclipse.org/bugs/show_bug.cgi?id=334050
//			// if the data is stale, return true and attempt to update again in the near
//			// future
//			J2EEComponentClasspathUpdater.getInstance().queueUpdate(owner.getProject());
//			return true;
//		}

		final List currentEntries = computeClasspathEntries(false);
		boolean outOfDate = !this.entries.equals(currentEntries);
		if (outOfDate) {
			WebAppLibrariesStore.flush(project.getName());
		}
		return outOfDate;
	}

	@Override
	public void refresh(boolean forceUpdate) {
//		if (IDependencyGraph.INSTANCE.isStale()) {
//			// avoid deadlock https://bugs.eclipse.org/bugs/show_bug.cgi?id=334050
//			// if the data is stale abort and attempt to update again in the near future
//			J2EEComponentClasspathUpdater.getInstance().queueUpdate(owner.getProject());
//			return;
//		}

		if (forceUpdate || isOutOfDate()) {
			refresh();
		}
	}

	@Override
	public void refresh() {
//		if (IDependencyGraph.INSTANCE.isStale()) {
//			// avoid deadlock https://bugs.eclipse.org/bugs/show_bug.cgi?id=335645
//			// if the data is stale abort and attempt to update again in the near future
//			J2EEComponentClasspathUpdater.getInstance().queueUpdate(owner.getProject());
//			return;
//		}

		(new WebAppLibrariesContainer(this.path, this.owner)).install();
	}

	private static Map<String, Object> referenceOptions = new HashMap<String, Object>();
	static {
		referenceOptions.put("GET_JAVA_REFS", Boolean.FALSE); //$NON-NLS-1$
	}

//	protected List<IVirtualReference> consumedReferences;
//
//	@Override
//	protected IVirtualReference[] computeReferences(IVirtualComponent vc) {
//		IVirtualReference[] baseRefs = ((VirtualComponent) vc).getReferences(referenceOptions);
//		List<IVirtualReference> refs = new ArrayList<IVirtualReference>();
//		for (IVirtualReference ref : baseRefs) {
//			if (ref.getDependencyType() == IVirtualReference.DEPENDENCY_TYPE_USES) {
//				refs.add(ref);
//			} else {
//				if (ref.getRuntimePath().equals(paths[0].makeAbsolute())) {
//					if (consumedReferences == null) {
//						consumedReferences = new ArrayList<IVirtualReference>();
//					}
//					consumedReferences.add(ref);
//				}
//			}
//		}
//		return refs.toArray(new IVirtualReference[refs.size()]);
//	}

	@Override
	protected List computeClasspathEntries() {
		return computeClasspathEntries(true);
	}

	protected List computeClasspathEntries(boolean useRestoreState) {
		if (useRestoreState) {
			restoreState = WebAppLibrariesStore.getRestoreState(project.getName());
			if (restoreState != null) {
				return restoreState.paths;
			}
		}

		List<IPath> entries = super.computeClasspathEntries();
//		if (consumedReferences != null) {
//			for (IVirtualReference ref : consumedReferences) {
//				if (ref.getReferencedComponent() instanceof ClasspathContainerVirtualComponent) {
//					ClasspathContainerVirtualComponent cpvc = (ClasspathContainerVirtualComponent) ref
//							.getReferencedComponent();
//					if (cpvc != null) {
//						IClasspathContainer container = cpvc.getClasspathContainer();
//						if (container != null) {
//							IClasspathEntry[] newEntries = container.getClasspathEntries();
//							for (IClasspathEntry entry : newEntries) {
//								IPath entryPath = entry.getPath();
//								if (!entries.contains(entryPath)) {
//									entries.add(entryPath);
//								}
//							}
//						}
//					}
//				}
//			}
//		}

		String path = "/WebContent";
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(project.findMember(IModuleConstants.COMPONENT_FILE_PATH).getLocation().toFile());
			Element root = doc.getRootElement();
			List<Element> wbs = root.element("wb-module").elements("wb-resource");
			for (Element node : wbs) {
				if ("defaultRootSource".equals(node.attributeValue("tag"))) {
					path = node.attributeValue("source-path");
					break;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}

		IResource weblib = project.findMember(path + "/WEB-INF/lib");
		if (weblib == null) {
			weblib = project.findMember("WebRoot/WEB-INF/lib");
		}
		if (weblib != null) {
			File[] ls = weblib.getLocation().toFile().listFiles();
			for (File f : ls) {
				if (f.getName().toLowerCase().endsWith(".jar")) {
					String p = f.getAbsolutePath().replace(project.getLocation().toFile().getAbsolutePath(),
							File.separator);
					entries.add(project.findMember(p).getFullPath());
				}
			}
		}
		WebAppLibrariesStore.saveState(project.getName(), entries);
		return entries;
	}

	private static final IProject getProject(final IPath path, final IJavaProject jproject) {
		if (path.segmentCount() == 1) {
			return jproject.getProject();
		}
		final String name = path.segment(1);
		return ResourcesPlugin.getWorkspace().getRoot().getProject(name);
	}

	private static final class Resources extends NLS {
		public static String label;
		public static String labelWithProject;
		public static String verify;

		static {
			initializeMessages(WebAppLibrariesContainer.class.getName(), Resources.class);
		}
	}

}
