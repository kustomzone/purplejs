package org.purplejs.impl.executor;

import org.purplejs.impl.util.JsObjectConverter;
import org.purplejs.resource.ResourcePath;
import org.purplejs.value.ScriptValue;

public final class ExecutionContext
{
    private final ScriptExecutor executor;

    private final ResourcePath resource;

    private ResourceResolver resourceResolver;

    public ExecutionContext( final ScriptExecutor executor, final ResourcePath resource )
    {
        this.executor = executor;
        this.resource = resource;
        this.resourceResolver = new ResourceResolver( this.executor.getSettings().getResourceLoader(), this.resource );
    }

    public Object newBean( final String type )
        throws Exception
    {
        final Class<?> clz = Class.forName( type, true, this.executor.getSettings().getClassLoader() );
        return clz.newInstance();
    }

    public ResourcePath getResource()
    {
        return this.resource;
    }

    public Object require( final String path )
    {
        final ResourcePath key = this.resourceResolver.resolveJs( path );
        return this.executor.executeRequire( key );
    }

    public ResourcePath resolve( final String path )
    {
        return this.resourceResolver.resolve( path );
    }

    public ScriptValue toScriptValue( final Object value )
    {
        return this.executor.newScriptValue( value );
    }

    public Object toNativeObject( final Object value )
    {
        return JsObjectConverter.toJs( value );
    }

    public void registerMock( final String name, final Object value )
    {
        this.executor.registerMock( name, value );
    }
}
