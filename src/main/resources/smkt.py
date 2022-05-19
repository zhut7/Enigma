class Rotor(object):
    def __init__(self, index):
        self.pos = 'A'  # pos means the rotor's state of rotation. It can be reset when the key is given.
        self.index = index  # index means the rotor's position in an Enigma Machine.
        # It should be fixed when the machine is created.
        self.encode_offset = 0  # per character encoding offset which can be calculated by pos.

    def __call__(self, x):
        return self.encoding_single_character(x)

    def init(self, key):
        """
        :param key: single capital letter used to initiate rotor's state of rotation
        :return: None
        """
        self.pos = key
        self.reset_encode_offset()

    def reset_encode_offset(self):
        """
        :return: None
        """
        self.encode_offset = ord(self.pos) - 65

    def encoding_single_character(self, x):
        """
        :param x: the english char need to be encoded.
        :return: encoded char.
        """
        if ord('a') <= ord(x) <= ord('z'):
            tmp = ord(x) - ord('a') + self.encode_offset
            e_char = chr(tmp % 26 + ord('a'))
        elif ord('A') <= ord(x) <= ord('Z'):
            tmp = ord(x) - ord('A') + self.encode_offset
            e_char = chr(tmp % 26 + ord('A'))
        else:
            return False
        return e_char

    def decoding_single_character(self, x):
        """
        :param x: the english char need to be encoded.
        :return: encoded char.
        """
        if ord('a') <= ord(x) <= ord('z'):
            tmp = ord(x) - ord('a') - self.encode_offset
            e_char = chr(tmp % 26 + ord('a'))
        elif ord('A') <= ord(x) <= ord('Z'):
            tmp = ord(x) - ord('A') - self.encode_offset
            e_char = chr(tmp % 26 + ord('A'))
        else:
            return False
        return e_char

    def progression(self, flag=True):
        """
        Detects if this rotor needs to be rotated.
        :param flag: signal from former rotor(index == 0 will always get 'True' signal).
        :return: signal passed to next rotor.
        """
        if flag:
            self.pos = chr(((ord(self.pos) - 65 + 1) % 26) + 65)
            self.reset_encode_offset()
            return self.pos == 'A'  # omit True signal
        return False

class EnigmaMachine(object):
    def __init__(self, keys):
        """
        :param keys: keys for encoding and decoding
        """
        self.keys = keys
        self.rotor_num = len(keys)
        self.rotors = [None for _ in range(self.rotor_num)]
        self.init(self.keys)

    def init(self, keys):
        """
        Create a EnigmaMachine.
        The machine has len(keys) rotors.
        :param keys: A Capital Str that for encoding and decoding
        :return: None
        """
        for i in range(self.rotor_num):
            r = Rotor(i)
            r.init(keys[i])
            self.rotors[i] = r
        return None

    def encoding_single_character(self, x):
        y = x
        for i in range(self.rotor_num):
            y = self.rotors[i].encoding_single_character(y)
            if not y:
                return False
        return y

    def decoding_single_character(self, x):
        y = x
        for i in reversed(range(self.rotor_num)):
            y = self.rotors[i].decoding_single_character(y)
            if not y:
                return False
        return y

    def step(self, rotor_index):
        """Recursively progress every rotor"""
        if rotor_index == 0:
            return self.rotors[rotor_index].progression(True)
        return self.rotors[rotor_index].progression(self.step(rotor_index - 1))

    def coding(self, x_sentence, encoding=True):
        """
        X -> Y
        Every single chr encoded over, step one on rotors to change the rotation state.(26 Decimal)
        :param x_sentence: The str need to be encoded.
        :return: The str encoded
        """
        if encoding:
            print('start encoding...')
            y_sentence = ''
            for x in x_sentence:
                y = self.encoding_single_character(x)
                if y:
                    y_sentence += y
                    self.step(self.rotor_num - 1)  # forward decimal
                else:
                    y_sentence +=x
            return y_sentence
        else:
            y_sentence = ''
            for x in x_sentence:
                y = self.decoding_single_character(x)
                if y:
                    y_sentence += y
                    self.step(self.rotor_num - 1)  # forward decimal
                else:
                    y_sentence += x
            print('start decoding...')
            return y_sentence

def web_encoding(key,txt,encoding):
    machine = EnigmaMachine(key)
    result = machine.coding(txt,encoding)
    print(result)
    return result

# web_encoding('abc','voa yrd',0)