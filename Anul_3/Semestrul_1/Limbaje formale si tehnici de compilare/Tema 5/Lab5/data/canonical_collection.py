from sre_parse import State

from data.analysis_element import AnalysisElement
from data.grammar import Grammar
from data.rule import RuleNonTerminal, RuleTerminal, RuleComponent


class State:
    def __init__(self, grammar:Grammar, id: int, elems: list[AnalysisElement]):
        self.grammar = grammar
        self.id = id
        self.elems: list[AnalysisElement] = elems
        self.closure: list[AnalysisElement] = list(elems)

    def build_closure(self):
        tmp_elems: list[AnalysisElement] = list(self.elems)
        new_elems: list[AnalysisElement] = []

        while True:
            for elem in tmp_elems:
                next = elem.get_after_dot()
                if not isinstance(next, RuleNonTerminal): continue

                beta = self.grammar.first1_seq(elem.rule.rval[elem.dot + 1:])
                if len(beta) == 0:
                    beta:list[any] = [elem.u_pred]
                #print(list(map(str, beta)))

                for rule in self.grammar.get_derivations_of(next):
                    for b in beta:
                        a_elem = AnalysisElement(rule, 0, b)

                        exists = False
                        for existing_elem in tmp_elems:
                            if existing_elem == a_elem:
                                exists=True
                        if not exists:
                            new_elems.append(a_elem)
            tmp_elems += new_elems
            tmp_elems = list(set(tmp_elems))

            if len(new_elems) == 0:
                break
            new_elems = []

        self.closure = tmp_elems


    def goto(self, t:RuleNonTerminal | RuleTerminal):
        elems = list(filter(lambda a:a.get_after_dot()==t, self.closure))
        return State(self.grammar, -1, list(map(lambda e:e.advance_dot(), elems)))

    def get_transitions(self):
        # filter(None, x) -> elementele din x care nu sunt None
        # filter(lambda t:t is not None,x)
        # S->[A B C . , $]
        return list(set(filter(None, map(lambda x:x.get_after_dot(), self.closure))))


    def __str__(self):
        s = f"State I{self.id}"
        for e in self.closure:
            s += "\n  "+str(e)
        return s


    def is_equivalent(self, other: State) -> bool:
        for x in self.elems:
            if x not in other.elems:
                return False
        for x in other.elems:
            if x not in self.elems:
                return False
        return True


class CanonicalCollection:  # Represents the LR(1) canonical collection of states for a grammar
    def __init__(self, grammar: Grammar):
        self.grammar: Grammar = grammar  # Grammar used for constructing the collection

        # Initialize state I0, the starting state, which contains the start rule with the dot before it.
        i0 = State(grammar, 0, [AnalysisElement(grammar.rules[0])])
        i0.build_closure()  # Calculate the closure of the initial state

        # Transition map: (state, symbol) → next state
        self.transitions: dict[tuple[State, RuleComponent], State] = {}

        # List of all discovered states
        self.states: list[State] = [i0]
        self.__state_id = 1  # ID counter for newly discovered states

        # Discover all states and transitions starting from I0
        self.discover()

    def get_equivalent_state(self, new_state: State):
        """
        Checks if a newly created state is equivalent to any of the existing states.
        If equivalent, returns the existing state; otherwise, returns None.
        """
        for s in self.states:
            if s.is_equivalent(new_state):
                return s
        return None

    def discover(self):
        """
        Discovers all states and transitions using breadth-first search (BFS).
        For each state, it calculates the possible transitions and adds the resulting state to the collection.
        """
        new_states = []  # List to store newly discovered states

        while True:
            # Traverse through each state
            for state in self.states:
                # For each symbol in the state, calculate the transition
                for sym in state.get_transitions():
                    # Perform the goto operation to compute the new state resulting from the transition
                    new_state = state.goto(sym)

                    # Check if the new state is equivalent to an existing state
                    eq_state = self.get_equivalent_state(new_state)
                    if eq_state is None:  # If no equivalent state exists, create a new one
                        new_state.id = self.__state_id
                        self.__state_id += 1  # Increment the state ID for uniqueness
                        new_states.append(new_state)
                        new_state.build_closure()  # Calculate closure for the new state
                    else:
                        new_state = eq_state  # Use the existing equivalent state

                    # Store the transition in the dictionary
                    self.transitions[state, sym] = new_state

            # If no new states were added in this iteration, stop the loop
            if len(new_states) == 0:
                break

            # Add the newly discovered states to the main list of states
            self.states += new_states
            new_states = []  # Reset new_states for the next iteration

    def __str__(self):
        """
        Generates a string representation of the canonical collection, including all states and transitions.
        """
        s = ""
        for state in self.states:
            s += str(state) + "\n"
        for k, v in self.transitions.items():
            s += f"  (I{k[0].id},{k[1]}) --> I{v.id}\n"
        return s
