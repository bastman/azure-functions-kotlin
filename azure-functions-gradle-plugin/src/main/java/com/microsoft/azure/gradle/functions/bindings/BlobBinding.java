/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.gradle.functions.bindings;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.microsoft.azure.functions.annotation.BlobInput;
import com.microsoft.azure.functions.annotation.BlobOutput;
import com.microsoft.azure.functions.annotation.BlobTrigger;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BlobBinding extends StorageBaseBinding {
    public static final String BLOB_TRIGGER = "blobTrigger";
    public static final String BLOB = "blob";

    private String path = "";

    public BlobBinding(final BlobTrigger blobTrigger) {
        super(blobTrigger.name(), BLOB_TRIGGER, Direction.IN, blobTrigger.dataType());

        path = blobTrigger.path();
        setConnection(blobTrigger.connection());
    }

    public BlobBinding(final BlobInput blobInput) {
        super(blobInput.name(), BLOB, Direction.IN, blobInput.dataType());

        path = blobInput.path();
        setConnection(blobInput.connection());
    }

    public BlobBinding(final BlobOutput blobOutput) {
        super(blobOutput.name(), BLOB, Direction.OUT, blobOutput.dataType());

        path = blobOutput.path();
        setConnection(blobOutput.connection());
    }

    @JsonGetter
    public String getPath() {
        return path;
    }
}
