package com.zenith.command;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.zenith.Shared.COMMAND_MANAGER;

@Getter
@Setter
public class CommandUsage {
    private final String name;
    private final CommandCategory category;
    private final String description;
    private final List<String> usageLines;
    private final List<String> aliases;

    private CommandUsage(final String name, final CommandCategory category, final String description, final List<String> usageLines, final List<String> aliases) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.usageLines = usageLines;
        this.aliases = aliases;
    }

    public static CommandUsage simple(final String name, final CommandCategory category, final String description) {
        return new CommandUsage(name, category, description, Collections.emptyList(), Collections.emptyList());
    }

    public static CommandUsage simpleAliases(final String name, final CommandCategory category, final String description, final List<String> aliases) {
        return new CommandUsage(name, category, description, Collections.emptyList(), aliases);
    }

    public static CommandUsage args(final String name, final CommandCategory category, final String description, final List<String> usageLines) {
        return new CommandUsage(name, category, description, usageLines, Collections.emptyList());
    }

    public static CommandUsage full(final String name, final CommandCategory category, final String description, final List<String> usageLines, final List<String> aliases) {
        return new CommandUsage(name, category, description, usageLines, aliases);
    }

    public String serialize(CommandSource commandSource) {
        return this.description
                + usageLines.stream()
                .map(line -> "\n  " + COMMAND_MANAGER.getCommandPrefix(commandSource) + name + " " + line)
                .collect(Collectors.joining());
    }

    public String shortSerialize(CommandSource commandSource) {
        String result = COMMAND_MANAGER.getCommandPrefix(commandSource) + this.name;
        if (!aliases.isEmpty()) {
            result += aliases.stream()
                    .collect(Collectors.joining(" / " + COMMAND_MANAGER.getCommandPrefix(commandSource),
                            " / " + COMMAND_MANAGER.getCommandPrefix(commandSource),
                            ""));
        }
        return result;
    }
}
