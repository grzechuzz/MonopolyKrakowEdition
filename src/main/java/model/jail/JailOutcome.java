package model.jail;

public sealed interface JailOutcome permits PaidOut, RolledDouble, UsedCard, RemainInJail { }


