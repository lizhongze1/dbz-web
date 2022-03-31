package com.ruoyi.sync.service.impl;

import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.runtime.WorkerConfig;
import org.apache.kafka.connect.runtime.standalone.StandaloneConfig;
import org.apache.kafka.connect.storage.FileOffsetBackingStore;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 　  * @className: FileOffsetBackingStoreExt
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2021/01/07 14:22
 *
 */
@Service
public class FileOffsetBackingStoreExt extends FileOffsetBackingStore {

    private File file;
    @Override
    public void configure(WorkerConfig config) {
        super.configure(config);
        file = new File(config.getString(StandaloneConfig.OFFSET_STORAGE_FILE_FILENAME_CONFIG));
    }
    @Override
    protected void save() {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file))) {
            Map<byte[], byte[]> raw = new HashMap<>();
            for (Map.Entry<ByteBuffer, ByteBuffer> mapEntry : data.entrySet()) {
                byte[] key = (mapEntry.getKey() != null) ? mapEntry.getKey().array() : null;
                byte[] value = (mapEntry.getValue() != null) ? mapEntry.getValue().array() : null;
                raw.put(key, value);
            }
            os.writeObject(raw);
        } catch (IOException e) {
            throw new ConnectException(e);
        }
    }
}
