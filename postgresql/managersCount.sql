CREATE OR REPLACE FUNCTION managersCount()
RETURNS INTEGER AS $$
DECLARE
    contador INTEGER;
BEGIN
    EXECUTE 'select count(*) from usuarios where rol=''MANAGER''' INTO contador;
    RETURN contador;
END;
$$ LANGUAGE plpgsql;