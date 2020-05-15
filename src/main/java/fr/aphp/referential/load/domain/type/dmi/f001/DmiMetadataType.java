package fr.aphp.referential.load.domain.type.dmi.f001;

public enum DmiMetadataType {
    LISTE_EN_SUS,
    OUI;

    public String representation() {
        return name().toLowerCase();
    }
}
