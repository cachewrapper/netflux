package org.cachewrapper.api.velocity.server.picker;

import org.cachewrapper.network.controller.server.picker.ServerPicker;

public record FirstJoinPicker(
        String serverType,
        Class<? extends ServerPicker> serverPickerType
) {
}
