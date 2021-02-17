package org.devignite.reportBuilder.dataProvider;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.devignite.reportBuilder.dataProvider.abstractions.DataProviderSettings;

@Data
@EqualsAndHashCode(callSuper = false)
public class MongoDataProviderSettings extends DataProviderSettings {
    private String sourceCollectionName;
}
