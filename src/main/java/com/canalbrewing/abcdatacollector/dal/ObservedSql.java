package com.canalbrewing.abcdatacollector.dal;

public interface ObservedSql {
    String SELECT_ALL_OBSERVED = "SELECT id, observed_name, user_id FROM observed WHERE user_id = ?";
    String SELECT_OBSERVED = "SELECT id, observed_name, user_id FROM observed WHERE id = ?";
    String SELECT_OBSERVED_DEFAULTS = "SELECT id, type_cd, value, active_fl FROM observed_defaults WHERE observed_id = ? AND active_fl = 'Y'";
    String INSERT_OBSERVED = "INSERT INTO observed (observed_name, user_id) VALUES (?, ?)";
    String INSERT_OBSERVED_DEFAULTS = "INSERT INTO observed_defaults (observed_id, type_cd, value, active_fl) VALUES (?, ?, ?, ?)";
    String UPDATE_OBSERVED = "UPDATE observed SET observed_name = ? WHERE id = ?";
    String UPDATE_OBSERVED_DEFAULT = "UPDATE observed_defaults SET value = ? WHERE id = ?";
    String DELETE_OBSERVED_DEFAULT = "DELETE FROM observed_defaults WHERE id = ?";
    String DELETE_OBSERVED = "DELETE FROM observed WHERE id = ?";
    String DELETE_OBSERVED_DEFAULTS = "UPDATE observed_defaults SET active_fl = 'N' WHERE id = ?";

    String[] DEFAULT_ANTECEDENTS = {
            "Asked Question", "Attention Required", "Bored", "Demand Placed", "Frustrated", "Told No", "Transition", "Given Something", "Wanted Something"
    };

    String[] DEFAULT_BEHAVIORS = {
            "Bit Self", "Bit Someone", "Cried", "Ran Away", "Scratched Self", "Scratched Someone", "Screamed", "Self Injurious", "Spit", "Tantrum", "Threw Food", "Threw Object", "Whined", "Yelled", "Swore"
    };

    String[] DEFAULT_CONSEQUENCES = {
            "Apologized", "Blocked", "Gave In", "Ignored", "Kept Demand", "Physically Restrained", "Redirected Physically", "Redirected Verbally", "Removed", "Reprimanded", "Separated"
    };

    String[] DEFAULT_LOCATIONS = {
            "Home", "School", "Playground", "Store"
    };
}
