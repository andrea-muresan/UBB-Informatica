from __future__ import annotations
from data.rule import *
from data.grammar import *

class EndOfWord:
    """
    Represents the end of a word or input in parsing algorithms.
    This class is used to signify the end of a string or sequence during parsing.
    """
    def __str__(self): return "$"

    def __eq__(self, other):
        return isinstance(other, EndOfWord)

    def __hash__(self): return hash(str(self))

class AnalysisElement:
    """
    Represents a single analysis element in a parsing process. It consists of a rule,
    a dot that indicates the current position within the rule's right-hand side, and a
    lookahead symbol (u_pred) that helps to predict the next symbol during parsing.
    """
    def __init__(self, rule: Rule, dot: int = 0, u_pred: EndOfWord | RuleTerminal | str = EndOfWord()):
        # Handle the case where u_pred is provided as a string.
        if isinstance(u_pred, str):
            if len(u_pred) != 1:
                raise ValueError("u-prediction length must be 1")
            u_pred = RuleTerminal(u_pred)
        # Ensure the dot position is valid, i.e., between 0 and the length of the rule's RHS.
        if dot < 0 or dot > len(rule.rval):
            raise ValueError(f"Invalid dot position: {dot}. Must be between {0} and {len(rule.rval)}")
        self.rule: Rule = rule
        self.dot: int = dot
        self.u_pred: EndOfWord | RuleTerminal = u_pred

    def get_after_dot(self):
        if self.dot == len(self.rule.rval):
            return None
        return self.rule.rval[self.dot]

    def advance_dot(self) -> AnalysisElement:
        return self.__class__(self.rule, self.dot+1, self.u_pred)

    def is_dot_at_end(self):
        return self.dot == len(self.rule.rval)

    def __eq__(self, other):
        if not isinstance(other, AnalysisElement):
            return False
        return self.rule == other.rule and self.dot == other.dot and self.u_pred == other.u_pred

    def __str__(self):
        r = f"{self.rule.lval} -> "
        for i in range(len(self.rule.rval)):
            if self.dot==i:
                r += ". "
            r += str(self.rule.rval[i])+" "
        if self.dot == len(self.rule.rval):
            r += ". "
        return f"[{r}, {self.u_pred}]"

    def __hash__(self):
        return hash(str(self))