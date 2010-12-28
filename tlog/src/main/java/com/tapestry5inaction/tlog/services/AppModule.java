package com.tapestry5inaction.tlog.services;

import com.tapestry5inaction.tlog.entities.Blog;
import com.tapestry5inaction.tlog.pages.SidebarBlocks;
import com.tapestry5inaction.tlog.services.impl.*;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.services.ApplicationStateContribution;
import org.apache.tapestry5.services.ApplicationStateCreator;
import org.apache.tapestry5.services.ApplicationStateManager;

public class AppModule {

    public static void bind(final ServiceBinder binder) {
        binder.bind(Authenticator.class, AuthenticatorImpl.class);
        binder.bind(GravatarService.class, GravatarServiceImpl.class);
        binder.bind(BlogService.class, BlogServiceImpl.class);
        binder.bind(SidebarBlockSource.class, SidebarBlockSourceImpl.class);
    }

    @Contribute(ApplicationStateManager.class)
    public static void contribute(
            final MappedConfiguration<Class, ApplicationStateContribution> configuration,
            final BlogService blogService) {

        final ApplicationStateCreator<Blog> creator = new ApplicationStateCreator<Blog>() {
            public Blog create() {
                return blogService.findBlog();
            }

        };
        configuration.add(Blog.class, new ApplicationStateContribution(
                PersistenceConstants.SESSION, creator));
    }

    @Contribute(SidebarBlockSource.class)
    public static void contribute(
            final OrderedConfiguration<SidebarBlockContribution> configuration) {

        configuration.add("Pages", new SidebarBlockContribution(
                SidebarBlocks.class, "pages"), "before:*");

        configuration.add("Archives", new SidebarBlockContribution(
                SidebarBlocks.class, "archives"), "after:Pages");

        configuration.add("Categories", new SidebarBlockContribution(
                SidebarBlocks.class, "categories"), "after:Archives");

        configuration.add("Blogroll", new SidebarBlockContribution(
                SidebarBlocks.class, "blogroll"), "after:Categories");

        configuration.add("Meta", new SidebarBlockContribution(
                SidebarBlocks.class, "meta"), "after:*");
    }

    @Startup
    public static void initDemoData(@Autobuild
                                    final DemoDataSource source) {
        source.create();
    }
}
