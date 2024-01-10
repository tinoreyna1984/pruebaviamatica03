CREATE OR REPLACE FUNCTION operatorsCount()
RETURNS INTEGER AS $$
DECLARE
    contador INTEGER;
BEGIN
    EXECUTE 'select count(*) from usuarios where rol=''USER''' INTO contador;
    RETURN contador;
END;
$$ LANGUAGE plpgsql;