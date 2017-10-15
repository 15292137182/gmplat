package com.bcx.plat.core.morebatis.configuration.builder;

import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.configuration.EntityEntry;
import java.util.List;

public interface EntityEntriesBuilder {

  List<EntityEntry> getEntries(MoreBatis moreBatis);
}
