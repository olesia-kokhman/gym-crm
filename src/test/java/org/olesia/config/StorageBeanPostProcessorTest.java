package org.olesia.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StorageBeanPostProcessorTest {

    @Test
    void storage_ShouldBeInitializedFromFile_OnContextStart() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(StorageConfig.class);

        Map<String, Map<UUID, Object>> storage =
                context.getBean("storage", Map.class);

        assertNotNull(storage);

        assertTrue(storage.containsKey("trainer"));
        assertTrue(storage.containsKey("trainee"));
        assertTrue(storage.containsKey("training"));

        assertEquals(1, storage.get("trainer").size());
        assertEquals(1, storage.get("trainee").size());
        assertEquals(1, storage.get("training").size());

        context.close();
    }
}